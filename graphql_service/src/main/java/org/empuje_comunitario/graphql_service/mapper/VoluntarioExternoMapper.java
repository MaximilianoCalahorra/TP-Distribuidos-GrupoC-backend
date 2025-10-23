package org.empuje_comunitario.graphql_service.mapper;

import org.empuje_comunitario.graphql_service.dto.VoluntarioExternoDTO;
import org.empuje_comunitario.graphql_service.model.VoluntarioExterno;

public class VoluntarioExternoMapper {
    // =======================
    // Entidad <-> DTOs
    // =======================

    // VoluntarioExternoDTO <-> VoluntarioExterno
    public static VoluntarioExternoDTO aDTO(VoluntarioExterno entidad) {
        if (entidad == null) return null;

        VoluntarioExternoDTO dto = new VoluntarioExternoDTO();

        if (entidad.getIdVoluntarioExterno() != null && entidad.getIdVoluntarioExterno() > 0) {
            dto.setIdVoluntarioExterno(entidad.getIdVoluntarioExterno());
        }

        dto.setIdVoluntarioOrigen(entidad.getIdVoluntarioOrigen());
        dto.setNombre(entidad.getNombre());
        dto.setApellido(entidad.getApellido());
        dto.setTelefono(entidad.getTelefono());
        dto.setEmail(entidad.getEmail());
        dto.setIdOrganizacion(entidad.getIdOrganizacion());

        return dto;
    }

    public static VoluntarioExterno aEntidad(VoluntarioExternoDTO dto) {
        if (dto == null) return null;

        VoluntarioExterno entidad = new VoluntarioExterno();

        if (dto.getIdVoluntarioOrigen() != null && !dto.getIdVoluntarioOrigen().isEmpty()) {
            entidad.setIdVoluntarioOrigen(dto.getIdVoluntarioOrigen());
        }

        entidad.setNombre(dto.getNombre());
        entidad.setApellido(dto.getApellido());
        entidad.setTelefono(dto.getTelefono());
        entidad.setEmail(dto.getEmail());
        entidad.setIdOrganizacion(dto.getIdOrganizacion());

        return entidad;
    }
}
