package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import proto.dtos.CrearEventoSolidarioProto;
import proto.dtos.ModificarEventoSolidarioProto;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoSolidario;

public class EventoSolidarioMapper {

    // =======================
    // Entidad <-> DTOs
    // =======================

    // CrearEventoSolidarioDTO <-> EventoSolidario
    public static CrearEventoSolidarioDTO aDTO(EventoSolidario entidad) {
    	//TODO
    }
    
    public static EventoSolidario aEntidad(CrearEventoSolidarioDTO dto) {
    	//TODO
    }

    // ModificarEventoSolidarioDTO <-> EventoSolidario
    public static ModificarEventoSolidarioDTO aModificarEventoSolidarioDTO(EventoSolidario entidad) {
    	//TODO
    }
    
    public static EventoSolidario aEntidad(ModificarEventoSolidarioDTO dto) {
    	//TODO
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