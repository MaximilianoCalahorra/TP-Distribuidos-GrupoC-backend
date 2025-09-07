package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import proto.dtos.CrearEventoSolidarioProto;
import proto.dtos.ModificarEventoSolidarioProto;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoSolidario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearEventoSolidarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.ModificarEventoSolidarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.MiembroDTO;

public class EventoSolidarioMapper {

    // =======================
    // Entidad <-> DTOs
    // =======================

    // CrearEventoSolidarioDTO <-> EventoSolidario
    public static CrearEventoSolidarioDTO aDTO(EventoSolidario entidad) {
        CrearEventoSolidarioDTO dto = new CrearEventoSolidarioDTO();
        dto.setNombre(entidad.getNombre());
        dto.setDescripcion(entidad.getDescripcion());
        dto.setFechaHora(entidad.getFechaHora());
        dto.setMiembros(
                entidad.getMiembros().stream()
                        .map(UsuarioMapper::aMiembroDTO) // Asume un mapper de Usuario a MiembroDTO
                        .collect(Collectors.toList())
        );
        return dto;
    }
    
    public static EventoSolidario aEntidad(CrearEventoSolidarioDTO dto) {
        EventoSolidario entidad = new EventoSolidario();
        entidad.setNombre(dto.getNombre());
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setFechaHora(dto.getFechaHora());
        entidad.setMiembros(
                dto.getMiembros().stream()
                        .map(UsuarioMapper::aEntidad) // Asume un mapper de MiembroDTO a Usuario
                        .collect(Collectors.toList())
        );
        return entidad;
    }

    // ModificarEventoSolidarioDTO <-> EventoSolidario
    public static ModificarEventoSolidarioDTO aModificarEventoSolidarioDTO(EventoSolidario entidad) {
        ModificarEventoSolidarioDTO dto = new ModificarEventoSolidarioDTO();
        dto.setIdEvento(entidad.getIdEventoSolidario());
        dto.setNombre(entidad.getNombre());
        dto.setDescripcion(entidad.getDescripcion());
        dto.setFechaHora(entidad.getFechaHora());
        dto.setMiembros(
                entidad.getMiembros().stream()
                        .map(UsuarioMapper::aMiembroDTO) // Asume un mapper de Usuario a MiembroDTO
                        .collect(Collectors.toList())
        );
        return dto;
    }
    
    public static EventoSolidario aEntidad(ModificarEventoSolidarioDTO dto) {
        EventoSolidario entidad = new EventoSolidario();
        entidad.setIdEventoSolidario(dto.getIdEvento());
        entidad.setNombre(dto.getNombre());
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setFechaHora(dto.getFechaHora());
        entidad.setMiembros(
                dto.getMiembros().stream()
                        .map(UsuarioMapper::aEntidad) // Asume un mapper de MiembroDTO a Usuario
                        .collect(Collectors.toList())
        );
        return entidad;
    }

    // =======================
    // DTOs <-> Protos
    // =======================

    // CrearEventoSolidarioDTO <-> CrearEventoSolidarioProto
    public static CrearEventoSolidarioDTO toCrearDTO(CrearEventoSolidarioProto proto) {
        if (proto == null) return null;
        CrearEventoSolidarioDTO dto = new CrearEventoSolidarioDTO();
        dto.setNombre(proto.getNombre());
        dto.setDescripcion(proto.getDescripcion());
        dto.setFechaHora(DateTimeMapper.desdeProto(proto.getFechaHora()));
        dto.setMiembros(
                proto.getMiembrosList().stream()
                     .map(UsuarioMapper::aMiembroDTO)
                     .toList()
        );
        return dto;
    }

    public static CrearEventoSolidarioProto toCrearProto(CrearEventoSolidarioDTO dto) {
        if (dto == null) return null;
        return CrearEventoSolidarioProto.newBuilder()
                .setNombre(dto.getNombre())
                .setDescripcion(dto.getDescripcion())
                .setFechaHora(DateTimeMapper.aProto(dto.getFechaHora()))
                .addAllMiembros(
                        dto.getMiembros().stream()
                           .map(UsuarioMapper::aMiembroProto)
                           .toList()
                )
                .build();
    }

    // ModificarEventoSolidarioDTO <-> ModificarEventoSolidarioProto
    public static ModificarEventoSolidarioDTO toModificarDTO(ModificarEventoSolidarioProto proto) {
        if (proto == null) return null;
        ModificarEventoSolidarioDTO dto = new ModificarEventoSolidarioDTO();
        dto.setNombre(proto.getNombre());
        dto.setFechaHora(DateTimeMapper.desdeProto(proto.getFechaHora()));
        dto.setMiembros(
                proto.getMiembrosList().stream()
                     .map(UsuarioMapper::aMiembroDTO)
                     .toList()
        );
        return dto;
    }

    public static ModificarEventoSolidarioProto toModificarProto(ModificarEventoSolidarioDTO dto) {
        if (dto == null) return null;
        return ModificarEventoSolidarioProto.newBuilder()
                .setNombre(dto.getNombre())
                .setFechaHora(DateTimeMapper.aProto(dto.getFechaHora()))
                .addAllMiembros(
                        dto.getMiembros().stream()
                           .map(UsuarioMapper::aMiembroProto)
                           .toList()
                )
                .build();
    }
}