package org.empuje_comunitario.graphql_service.service.implementations;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.empuje_comunitario.graphql_service.enums.RepartoDonacion;
import org.empuje_comunitario.graphql_service.graphql.mapper.LocalDateTimeMapper;
import org.empuje_comunitario.graphql_service.graphql.model.FiltroEventoBusquedaInputGraphQL;
import org.empuje_comunitario.graphql_service.graphql.model.InformeEventoMesGraphQL;
import org.empuje_comunitario.graphql_service.graphql.model.InformeEventoParticipacionGraphQL;
import org.empuje_comunitario.graphql_service.model.Donacion;
import org.empuje_comunitario.graphql_service.model.EventoSolidario;
import org.empuje_comunitario.graphql_service.model.Usuario;
import org.empuje_comunitario.graphql_service.repository.IDonacionRepository;
import org.empuje_comunitario.graphql_service.repository.IEventoRepository;
import org.empuje_comunitario.graphql_service.service.interfaces.IInformeEventoService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InformeEventoService implements IInformeEventoService {
    ///Atributos:
    private final IEventoRepository eventoRepository; //Repositorio para acceder a los eventos solidarios.
    private final IDonacionRepository donacionRepository; //Repositorio para acceder a las donaciones.

    ///Obtener informe de participación en eventos de la ONG agrupados por mes:
    @Override
    @Transactional(readOnly = true) //Transacción solo de lectura, no modifica la base de datos.
    public List<InformeEventoMesGraphQL> informeParticipacionEventos(String filtroJson) {
        //Mapear JSON recibido desde GraphQL a la clase de Java correspondiente:
    	ObjectMapper mapper = new ObjectMapper();
        FiltroEventoBusquedaInputGraphQL filtro;

        try {
            //Convertimos el string JSON a objeto Java para poder acceder a los filtros individualmente:
            filtro = mapper.readValue(filtroJson, FiltroEventoBusquedaInputGraphQL.class);
        } catch (Exception e) {
            //Si el JSON no se puede parsear, lanzamos excepción indicando el error:
            throw new RuntimeException("Error al parsear filtro JSON", e);
        }

        //Validar que se haya proporcionado un usuario, que es obligatorio para este informe:
        if (filtro.getUsuarioEmail() == null || filtro.getUsuarioEmail().isBlank()) {
            throw new IllegalArgumentException("El filtro debe incluir un usuario.");
        }

        //Inicializamos variables para las fechas del filtro, si fueron enviadas:
        LocalDateTime fechaHoraDesde = null;
        LocalDateTime fechaHoraHasta = null;

        //Si se proporcionó fecha de inicio, convertimos a LocalDateTime:
        if (filtro.getFechaHoraDesde() != null && !filtro.getFechaHoraDesde().isBlank()) {
            fechaHoraDesde = LocalDateTimeMapper.desdeString(filtro.getFechaHoraDesde());
        }
        //Si se proporcionó fecha de fin, convertimos a LocalDateTime:
        if (filtro.getFechaHoraHasta() != null && !filtro.getFechaHoraHasta().isBlank()) {
            fechaHoraHasta = LocalDateTimeMapper.desdeString(filtro.getFechaHoraHasta());
        }

        //Validar que el rango de fechas sea consistente solo si ambas fechas están presentes:
        if (fechaHoraDesde != null && fechaHoraHasta != null && fechaHoraHasta.isBefore(fechaHoraDesde)) {
            throw new IllegalArgumentException("La fecha y hora 'hasta' no puede ser anterior a la fecha y hora 'desde'.");
        }
        
        //Inicializamos la especificación base para la consulta JPA:
        Specification<EventoSolidario> spec = Specification.where(null);

        //Filtrar por fechas usando las variables finales necesarias para lambdas:
        final LocalDateTime desde = fechaHoraDesde;
        final LocalDateTime hasta = fechaHoraHasta;        
        
        //Si existe fecha desde, agregamos la condición a la spec:
        if (desde != null) {
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("fechaHora"), desde));
        }
        //Si existe fecha hasta, agregamos la condición a la spec:
        if (hasta != null) {
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("fechaHora"), hasta));
        }

        //Filtrar por usuario, buscando aquellos eventos donde el usuario es miembro:
        if (filtro.getUsuarioEmail() != null && !filtro.getUsuarioEmail().isBlank()) {
            spec = spec.and((root, query, cb) -> {
                //Hacemos join con la tabla de miembros del evento:
                Join<EventoSolidario, Usuario> miembrosJoin = root.join("miembros", JoinType.INNER);
                //Condición: email del miembro debe coincidir con el email del filtro:
                return cb.equal(miembrosJoin.get("email"), filtro.getUsuarioEmail());
            });
        }

        //Filtrar por si el evento tiene donaciones o no:
        if (filtro.getRepartoDonaciones() != null && filtro.getRepartoDonaciones() != RepartoDonacion.AMBOS) {
            spec = spec.and((root, query, cb) -> {
                //Creamos subquery para contar donaciones asociadas a cada evento:
                Subquery<Long> sub = query.subquery(Long.class);
                Root<Donacion> don = sub.from(Donacion.class);
                sub.select(cb.count(don));
                sub.where(cb.equal(don.get("eventoSolidario"), root));

                //Condición según el filtro: SI -> > 0, NO -> = 0:
                if (filtro.getRepartoDonaciones() == RepartoDonacion.SI) {
                    return cb.greaterThan(sub, 0L); //Solo eventos con donaciones.
                } else {
                    return cb.equal(sub, 0L); //Solo eventos sin donaciones.
                }
            });
        }

        //Ejecutamos la consulta y obtenemos la lista de eventos filtrados:
        List<EventoSolidario> eventos = eventoRepository.findAll(spec);

        //Mapear cada evento a su DTO GraphQL, incluyendo info de donaciones:
        List<InformeEventoParticipacionGraphQL> eventosDto = eventos.stream()
        	    .map(e -> {
        	        boolean tieneDonaciones;
        	        
        	        //Si se envío algún filtro de reparto de donaciones...
        	        if (filtro.getRepartoDonaciones() != null) {
        	        	switch (filtro.getRepartoDonaciones()) {
        	            case SI:
        	                //Si filtra por SI, sabemos que todos los eventos tienen donaciones:
        	                tieneDonaciones = true;
        	                break;
        	            case NO:
        	                //Si filtra por NO, sabemos que ningún evento tiene donaciones:
        	                tieneDonaciones = false;
        	                break;
        	            case AMBOS:
        	            default:
        	                //Si no filtra, chequeamos realmente si el evento tiene donaciones:
        	                tieneDonaciones = donacionRepository.existsByEventoSolidario(e);
        	                break;
        	        	}
        	        } else {
        	        	//Si no se envío el filtro, chequeamos si el evento tiene donaciones:
        	        	tieneDonaciones = donacionRepository.existsByEventoSolidario(e);
        	        }

        	        //Construimos el DTO GraphQL del evento:
        	        return new InformeEventoParticipacionGraphQL(
        	            e.getFechaHora().toString(), //Fecha del evento.
        	            e.getNombre(),               //Nombre del evento.
        	            e.getDescripcion(),          //Descripción del evento.
        	            tieneDonaciones             //Flag de donaciones.
        	        );
        	    })
        	    .collect(Collectors.toList());

        //Agrupar los eventos por mes:
        Map<Integer, List<InformeEventoParticipacionGraphQL>> agrupado = eventosDto.stream()
        	    .collect(Collectors.groupingBy(e -> LocalDateTime.parse(e.getDia()).getMonthValue()));

        //Devolver la lista final de DTOs agrupados por mes, ordenados de enero a diciembre:
        return agrupado.entrySet().stream()
            .map(entry -> new InformeEventoMesGraphQL(entry.getKey(), entry.getValue()))
            .sorted(Comparator.comparingInt(InformeEventoMesGraphQL::getMes))
            .collect(Collectors.toList());
    }
}
