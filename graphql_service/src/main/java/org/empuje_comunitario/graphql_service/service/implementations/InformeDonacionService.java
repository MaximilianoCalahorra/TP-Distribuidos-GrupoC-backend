package org.empuje_comunitario.graphql_service.service.implementations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.empuje_comunitario.graphql_service.enums.Categoria;
import org.empuje_comunitario.graphql_service.enums.EstadoEliminado;
import org.empuje_comunitario.graphql_service.graphql.mapper.LocalDateTimeMapper;
import org.empuje_comunitario.graphql_service.graphql.model.FiltroDonacionBusquedaInputGraphQL;
import org.empuje_comunitario.graphql_service.graphql.model.InformeDonacionGraphQL;
import org.empuje_comunitario.graphql_service.model.ItemDonacion;
import org.empuje_comunitario.graphql_service.model.TransferenciaDonacion;
import org.empuje_comunitario.graphql_service.service.interfaces.IInformeDonacionService;
import org.empuje_comunitario.graphql_service.service.interfaces.ITransferenciaDonacionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InformeDonacionService implements IInformeDonacionService {

    private final ITransferenciaDonacionRepository transferenciaRepo;

    @Value("${ong.id}")
    private String ongEmpujeComunitarioId;

    @Override
    public List<InformeDonacionGraphQL> informeDonaciones(FiltroDonacionBusquedaInputGraphQL filtro) {
        // Specification base
        Specification<TransferenciaDonacion> spec = Specification.where(null);

        // Filtrar por categoría
        if (filtro.getCategoria() != null && filtro.getCategoria() != Categoria.TODAS) {
            spec = spec.and((root, query, cb) -> {
                Join<TransferenciaDonacion, ItemDonacion> itemsJoin = root.join("items", JoinType.INNER);
                return cb.equal(itemsJoin.get("categoria"), filtro.getCategoria());
            });
        }

        // Filtrar por estado eliminado
        if (filtro.getEliminado() != null && filtro.getEliminado() != EstadoEliminado.AMBOS) {
            boolean eliminadoBool = filtro.getEliminado() == EstadoEliminado.SI;
            spec = spec.and((root, query, cb) -> cb.equal(root.get("eliminado"), eliminadoBool));
        }

        // Filtrar por fechas
        if (filtro.getFechaHoraAltaDesde() != null && !filtro.getFechaHoraAltaDesde().isBlank()) {
            LocalDateTime desde = LocalDateTimeMapper.desdeString(filtro.getFechaHoraAltaDesde());
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("fechaHora"), desde));
        }

        if (filtro.getFechaHoraAltaHasta() != null && !filtro.getFechaHoraAltaHasta().isBlank()) {
            LocalDateTime hasta = LocalDateTimeMapper.desdeString(filtro.getFechaHoraAltaHasta());
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("fechaHora"), hasta));
        }

        // Filtrar por tipo de transferencia (entrante o saliente)
        if (filtro.getTipoTransferencia() != null) {
            switch (filtro.getTipoTransferencia()) {
                case ENTRANTE:
                    spec = spec.and((root, query, cb) -> cb.equal(root.get("idOrganizacionReceptora"), ongEmpujeComunitarioId));
                    break;
                case SALIENTE:
                    spec = spec.and((root, query, cb) -> cb.equal(root.get("idOrganizacionDonante"), ongEmpujeComunitarioId));
                    break;
            }
        }

        // Obtener transferencias filtradas
        List<TransferenciaDonacion> transferencias = transferenciaRepo.findAll(spec);

        // Agrupar por categoría y eliminado
        Map<Categoria, Map<Boolean, Integer>> agrupado = new HashMap<>();
        for (TransferenciaDonacion t : transferencias) {
            boolean elim = t.isEliminado();
            for (ItemDonacion item : t.getItems()) {
                Categoria cat = item.getCategoria();
                int cantidad = item.getCantidad();
                agrupado.computeIfAbsent(cat, k -> new HashMap<>())
                        .merge(elim, cantidad, Integer::sum);
            }
        }

        // Transformar a GraphQL
        List<InformeDonacionGraphQL> resultado = new ArrayList<>();
        agrupado.forEach((cat, mapElim) ->
                mapElim.forEach((elim, total) ->
                        resultado.add(new InformeDonacionGraphQL(cat.name(), elim, total))
                )
        );

        return resultado;
    }
}