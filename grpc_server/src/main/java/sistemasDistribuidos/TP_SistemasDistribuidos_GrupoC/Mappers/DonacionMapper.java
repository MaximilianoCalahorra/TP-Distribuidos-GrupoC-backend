package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearDonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.DonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Donacion;
import proto.dtos.CrearDonacionProto;
import proto.dtos.DonacionProto;

public class DonacionMapper {

	// =======================
  // Entidad <-> DTOs
  // =======================

  // DonacionDTO <-> Donacion
  public static DonacionDTO aDTO(Donacion entidad) {
      if (entidad == null) return null;

      DonacionDTO dto = new DonacionDTO();

      dto.setDescripcion(entidad.getDescripcion());
      dto.setCantidad(entidad.getCantidad());
      dto.setCategoria(entidad.getCategoria());

      return dto;
  }

  public static Donacion aEntidad(DonacionDTO dto) {
      if (dto == null) return null;

      Donacion entidad = new Donacion();

      entidad.setDescripcion(dto.getDescripcion());
      entidad.setCantidad(dto.getCantidad());
      entidad.setCategoria(dto.getCategoria());

      return entidad;
  }

  // CrearDonacionDTO <-> Donacion
  public static CrearDonacionDTO aCrearDonacionDTO(Donacion entidad) {
      if (entidad == null) return null;

      CrearDonacionDTO dto = new CrearDonacionDTO();

      dto.setDescripcion(entidad.getDescripcion());
      dto.setCantidad(entidad.getCantidad());
      dto.setCategoria(entidad.getCategoria());

      return dto;
  }

  public static Donacion aEntidad(CrearDonacionDTO dto) {
      if (dto == null) return null;

      Donacion entidad = new Donacion();

      entidad.setDescripcion(dto.getDescripcion());
      entidad.setCantidad(dto.getCantidad());
      entidad.setCategoria(dto.getCategoria());

      return entidad;
  }

  // =======================
  // DTOs <-> Protos
  // =======================

  // DonacionDTO <-> DonacionDTO
  public static DonacionDTO aDTO(DonacionProto proto) {
    if (proto == null) return null;

    DonacionDTO dto = new DonacionDTO();
    dto.setDescripcion(proto.getDescripcion());
    dto.setCantidad(proto.getCantidad());
    dto.setCategoria(CategoriaMapper.aEnum(proto.getCategoria()));

      return dto;
  }

  public static DonacionProto aProto(DonacionDTO dto) {
      if (dto == null) return null;

      return DonacionProto.newBuilder()
              .setCategoria(CategoriaMapper.aProto(dto.getCategoria()))
              .setCantidad(dto.getCantidad())
              .build();
  }

  // CrearDonacionDTO <-> CrearDonacionProto
  public static CrearDonacionDTO aCrearDonacionDTO(CrearDonacionProto proto) {
      if (proto == null) return null;

      CrearDonacionDTO dto = new CrearDonacionDTO();
      dto.setIdEvento(proto.getIdEvento());
      dto.setDescripcion(proto.getDescripcion());
      dto.setCantidad(proto.getCantidad());dto.setCategoria(CategoriaMapper.aEnum(proto.getCategoria()));

      return dto;
  }

  public static CrearDonacionProto aCrearDonacionProto(CrearDonacionDTO dto) {
    if (dto == null) return null;

      return CrearDonacionProto.newBuilder()
              .setCategoria(CategoriaMapper.aProto(dto.getCategoria()))
              .setDescripcion(dto.getDescripcion())
              .setCantidad(dto.getCantidad())
              .build();
  }
}