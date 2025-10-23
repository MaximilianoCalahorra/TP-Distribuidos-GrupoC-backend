package org.empuje_comunitario.graphql_service.mapper;

import java.util.stream.Collectors;
import org.empuje_comunitario.graphql_service.model.EventoSolidario;
import org.empuje_comunitario.graphql_service.dto.EventoSolidarioDTO;

public class EventoSolidarioMapper {

    // =======================
    // Entidad <-> DTOs
    // =======================

    // EventoSolidarioDTO <-> EventoSolidario
    public static EventoSolidarioDTO aEventoSolidarioDTO(EventoSolidario entidad) {
        if (entidad == null) return null;

        EventoSolidarioDTO dto = new EventoSolidarioDTO();

        dto.setIdEventoSolidario(entidad.getIdEventoSolidario());
        dto.setNombre(entidad.getNombre());
        dto.setDescripcion(entidad.getDescripcion());
        dto.setFechaHora(entidad.getFechaHora());
        dto.setMiembros(
                entidad.getMiembros().stream()
                        .map(UsuarioMapper::aMiembroDTO)
                        .collect(Collectors.toList()));
        if (entidad.getVoluntariosExternos() != null) {
            dto.setVoluntariosExternos(
                    entidad.getVoluntariosExternos().stream()
                            .map(VoluntarioExternoMapper::aDTO)
                            .collect(Collectors.toList()));
        }
        dto.setPublicado(entidad.isPublicado());

        return dto;
    }

    public static EventoSolidario aEntidad(EventoSolidarioDTO dto) {
        if (dto == null) return null;

        EventoSolidario entidad = new EventoSolidario();

        entidad.setIdEventoSolidario(dto.getIdEventoSolidario());
        entidad.setNombre(dto.getNombre());
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setFechaHora(dto.getFechaHora());

        return entidad;
    }
}