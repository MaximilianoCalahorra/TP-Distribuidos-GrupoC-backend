package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import proto.dtos.inventario.ModificarInventarioProto;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Inventario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearInventarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.ModificarInventarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.InventarioDTO;
import proto.dtos.inventario.CrearInventarioProto;
import proto.dtos.inventario.InventarioProto;

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

  // CrearInventarioDTO <-> Inventario
  public static CrearInventarioDTO aCrearInventarioDTO(Inventario entidad) {
      if (entidad == null) return null;

      CrearInventarioDTO dto = new CrearInventarioDTO();

      dto.setCategoria(entidad.getCategoria());
      dto.setDescripcion(entidad.getDescripcion());
      dto.setCantidad(entidad.getCantidad());

      return dto;
  }

  public static Inventario aEntidad(CrearInventarioDTO dto) {
      if (dto == null) return null;

      Inventario entidad = new Inventario();

      entidad.setCategoria(dto.getCategoria());
      entidad.setDescripcion(dto.getDescripcion());
      entidad.setCantidad(dto.getCantidad());

      return entidad;
  }

  // ModificarInventarioDTO <-> Inventario
  public static ModificarInventarioDTO aModificarInventarioDTO(Inventario entidad) {
      if (entidad == null) return null;

      ModificarInventarioDTO dto = new ModificarInventarioDTO();

      dto.setIdInventario(entidad.getIdInventario());
      dto.setDescripcion(entidad.getDescripcion());
      dto.setCantidad(entidad.getCantidad());

      return dto;
  }

  public static Inventario aEntidad(ModificarInventarioDTO dto) {
	  if (dto == null) return null;
	
	  Inventario entidad = new Inventario();
	
	  entidad.setIdInventario(dto.getIdInventario());
	  entidad.setDescripcion(dto.getDescripcion());
	  entidad.setCantidad(dto.getCantidad());
	
	  return entidad;
  }

  // =======================
  // DTOs <-> Protos
  // =======================

  // InventarioDTO <-> InventarioProto
  public static InventarioDTO aDTO(InventarioProto proto) {
	  if (proto == null) return null;

	  InventarioDTO dto = new InventarioDTO();
	  dto.setIdInventario(proto.getIdInventario());
	  dto.setDescripcion(proto.getDescripcion());
	  dto.setCantidad(proto.getCantidad());
	  dto.setEliminado(proto.getEliminado());
	  dto.setCategoria(CategoriaMapper.aEnum(proto.getCategoria()));
   
      return dto;
  }

  public static InventarioProto aProto(InventarioDTO dto) {
      if (dto == null) return null;

      return InventarioProto.newBuilder()
    		  .setIdInventario(dto.getIdInventario())
              .setCategoria(CategoriaMapper.aProto(dto.getCategoria()))
              .setDescripcion(dto.getDescripcion())
              .setCantidad(dto.getCantidad())
              .setEliminado(dto.isEliminado())
              .build();
  }

  // CrearInventarioDTO <-> CrearInventarioProto
  public static CrearInventarioDTO aCrearInventarioDTO(CrearInventarioProto proto) {
      if (proto == null) return null;

      CrearInventarioDTO dto = new CrearInventarioDTO();
      
      dto.setDescripcion(proto.getDescripcion());
      dto.setCantidad(proto.getCantidad());
      dto.setCategoria(CategoriaMapper.aEnum(proto.getCategoria()));

      return dto;
  }

  public static CrearInventarioProto aCrearInventarioProto(CrearInventarioDTO dto) {
	  if (dto == null) return null;

	  return CrearInventarioProto.newBuilder()
              .setCategoria(CategoriaMapper.aProto(dto.getCategoria()))
              .setDescripcion(dto.getDescripcion())
              .setCantidad(dto.getCantidad())
              .build();
  }

  // ModificarInventarioDTO <-> ModificarInventarioProto
  public static ModificarInventarioDTO aModificarInventarioDTO(ModificarInventarioProto proto) {
	  if (proto == null) return null;

	  ModificarInventarioDTO dto = new ModificarInventarioDTO();
	  
	  dto.setIdInventario(proto.getIdInventario());
      dto.setDescripcion(proto.getDescripcion());
      dto.setCantidad(proto.getCantidad());

      return dto;
  }

  public static ModificarInventarioProto aModificarInventarioProto(ModificarInventarioDTO dto) {
	  if (dto == null) return null;

      return ModificarInventarioProto.newBuilder()
    		  .setIdInventario(dto.getIdInventario())
              .setDescripcion(dto.getDescripcion())
              .setCantidad(dto.getCantidad())
              .build();
  }
}