package org.empuje_comunitario.graphql_service.mapper;

import org.empuje_comunitario.graphql_service.dto.DonacionDTO;
import org.empuje_comunitario.graphql_service.model.Donacion;

public class DonacionMapper {

    // =======================
    // Entidad <-> DTOs
    // =======================

    // DonacionDTO <-> Donacion
    public static DonacionDTO aDTO(Donacion entidad) {
        if (entidad == null) return null;

        DonacionDTO dto = new DonacionDTO();

        dto.setDescripcion(entidad.getInventario().getDescripcion());
        dto.setCantidad(entidad.getCantidad());
        dto.setCategoria(entidad.getInventario().getCategoria());

        return dto;
    }
}