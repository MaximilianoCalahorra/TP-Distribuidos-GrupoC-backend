package org.empuje_comunitario.graphql_service.mapper;

import org.empuje_comunitario.graphql_service.model.Inventario;
import org.empuje_comunitario.graphql_service.dto.InventarioDTO;

public class InventarioMapper {

  // =======================
  // Entidad <-> DTOs
  // =======================

  // InventarioDTO <-> Inventario
  public static InventarioDTO aDTO(Inventario entidad) {
     if (entidad == null) return null;

     InventarioDTO dto = new InventarioDTO();

     dto.setIdInventario(entidad.getIdInventario());
     dto.setCategoria(entidad.getCategoria());
     dto.setDescripcion(entidad.getDescripcion());
     dto.setCantidad(entidad.getCantidad());
     dto.setEliminado(entidad.isEliminado());

     return dto;
  }

  public static Inventario aEntidad(InventarioDTO dto) {
      if (dto == null) return null;

      Inventario entidad = new Inventario();

      entidad.setIdInventario(dto.getIdInventario());
      entidad.setCategoria(dto.getCategoria());
      entidad.setDescripcion(dto.getDescripcion());
      entidad.setCantidad(dto.getCantidad());
      entidad.setEliminado(dto.isEliminado());

      return entidad;
  }
}