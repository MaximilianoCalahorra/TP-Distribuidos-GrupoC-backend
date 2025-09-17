package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearDonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.DonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Donacion;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoSolidario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Inventario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Usuario;

import java.time.LocalDateTime;

import proto.dtos.donacion.CrearDonacionProto;
import proto.dtos.donacion.DonacionProto;

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

    // CrearDonacionDTO <-> Donacion
    public static CrearDonacionDTO aCrearDonacionDTO(Donacion entidad) {
        if (entidad == null) return null;

        CrearDonacionDTO dto = new CrearDonacionDTO();

        dto.setIdEventoSolidario(entidad.getEventoSolidario().getIdEventoSolidario());
        dto.setCantidad(entidad.getCantidad());
        dto.setIdInventario(entidad.getInventario().getIdInventario());

        return dto;
    }

    public static Donacion aEntidad(CrearDonacionDTO dto, Inventario inventario, EventoSolidario evento, Usuario usuario) {
        if (dto == null) return null;

        Donacion entidad = new Donacion();

        entidad.setInventario(inventario);
        entidad.setEventoSolidario(evento);
        entidad.setUsuario(usuario);
        entidad.setCantidad(dto.getCantidad());
        entidad.setFechaHoraModificacion(LocalDateTime.now());

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
                .setDescripcion(dto.getDescripcion())
                .setCantidad(dto.getCantidad())
                .setCategoria(CategoriaMapper.aProto(dto.getCategoria()))
                .build();
    }

    // CrearDonacionDTO <-> CrearDonacionProto
    public static CrearDonacionDTO aCrearDonacionDTO(CrearDonacionProto proto) {
        if (proto == null) return null;

        CrearDonacionDTO dto = new CrearDonacionDTO();

        dto.setIdEventoSolidario(proto.getIdEventoSolidario());
        dto.setCantidad(proto.getCantidad());
        dto.setIdInventario(proto.getIdInventario());

        return dto;
    }

    public static CrearDonacionProto aCrearDonacionProto(CrearDonacionDTO dto) {
        if (dto == null) return null;

        return CrearDonacionProto.newBuilder()
                .setIdEventoSolidario(dto.getIdEventoSolidario())
                .setCantidad(dto.getCantidad())
                .setIdInventario(dto.getIdInventario())
                .build();
    }
}