package org.empuje_comunitario.graphql_service.mapper;

import org.empuje_comunitario.graphql_service.dto.ItemDonacionDTO;
import org.empuje_comunitario.graphql_service.model.ItemDonacion;

public class ItemDonacionMapper {
	
	// =======================
    // Entidad <-> DTOs
    // =======================
	
	// ItemDonacionDTO <-> ItemDonacion
    public static ItemDonacionDTO aDTO(ItemDonacion entidad) {
        if (entidad == null) return null;

        ItemDonacionDTO dto = new ItemDonacionDTO();
        
        dto.setIdItemDonacion(entidad.getIdItemDonacion());
        dto.setCategoria(entidad.getCategoria());
        dto.setDescripcion(entidad.getDescripcion());
        dto.setCantidad(entidad.getCantidad());
        dto.setOperacion(entidad.getOperacion());
        
        return dto;
    }

    public static ItemDonacion aEntidad(ItemDonacionDTO dto) {
        if (dto == null) return null;

        ItemDonacion entidad = new ItemDonacion();
        
        if (dto.getCantidad() > 0) {
        	entidad.setCantidad(dto.getCantidad());
        }
        
        entidad.setCategoria(dto.getCategoria());
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setOperacion(dto.getOperacion());

        return entidad;
    }
}
