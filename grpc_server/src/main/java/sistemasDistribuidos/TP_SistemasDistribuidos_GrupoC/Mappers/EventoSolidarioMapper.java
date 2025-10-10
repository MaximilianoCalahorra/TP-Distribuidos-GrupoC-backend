package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import java.util.stream.Collectors;
import proto.dtos.evento_solidario.CrearEventoSolidarioProto;
import proto.dtos.evento_solidario.EventoSolidarioProto;
import proto.dtos.evento_solidario.ModificarEventoSolidarioProto;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoSolidario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearEventoSolidarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.EventoSolidarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.ModificarEventoSolidarioDTO;

public class EventoSolidarioMapper {

    // =======================
    // Entidad <-> DTOs
    // =======================

    // CrearEventoSolidarioDTO <-> EventoSolidario
    public static CrearEventoSolidarioDTO aDTO(EventoSolidario entidad) {
        if (entidad == null) return null;

        CrearEventoSolidarioDTO dto = new CrearEventoSolidarioDTO();

        dto.setNombre(entidad.getNombre());
        dto.setDescripcion(entidad.getDescripcion());
        dto.setFechaHora(entidad.getFechaHora());
        dto.setMiembros(
                entidad.getMiembros().stream()
                        .map(UsuarioMapper::aMiembroDTO)
                        .collect(Collectors.toList())
        );

        return dto;
    }

    public static EventoSolidario aEntidad(CrearEventoSolidarioDTO dto) {
        if (dto == null) return null;

        EventoSolidario entidad = new EventoSolidario();

        entidad.setNombre(dto.getNombre());
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setFechaHora(dto.getFechaHora());
        entidad.setMiembros(
                dto.getMiembros().stream()
                        .map(UsuarioMapper::aEntidad)
                        .collect(Collectors.toList())
        );

        return entidad;
    }

    // ModificarEventoSolidarioDTO <-> EventoSolidario
    public static ModificarEventoSolidarioDTO aModificarEventoSolidarioDTO(EventoSolidario entidad) {
        if (entidad == null) return null;

        ModificarEventoSolidarioDTO dto = new ModificarEventoSolidarioDTO();

        dto.setIdEventoSolidario(entidad.getIdEventoSolidario());
        dto.setNombre(entidad.getNombre());
        dto.setFechaHora(entidad.getFechaHora());
        dto.setMiembros(
                entidad.getMiembros().stream()
                        .map(UsuarioMapper::aMiembroDTO)
                        .collect(Collectors.toList())
        );

        return dto;
    }

    public static EventoSolidario aEntidad(ModificarEventoSolidarioDTO dto) {
        if (dto == null) return null;

        EventoSolidario entidad = new EventoSolidario();

        entidad.setIdEventoSolidario(dto.getIdEventoSolidario());
        entidad.setNombre(dto.getNombre());
        entidad.setFechaHora(dto.getFechaHora());
        entidad.setMiembros(
                dto.getMiembros().stream()
                        .map(UsuarioMapper::aEntidad)
                        .collect(Collectors.toList())
        );

        return entidad;
    }

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
        dto.setVoluntariosExternos(
                entidad.getVoluntariosExternos().stream()
                        .map(VoluntarioExternoMapper::aDTO)
                        .collect(Collectors.toList()));
        
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

    // =======================
    // DTOs <-> Protos
    // =======================

    // CrearEventoSolidarioDTO <-> CrearEventoSolidarioProto
    public static CrearEventoSolidarioDTO aCrearDTO(CrearEventoSolidarioProto proto) {
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

    public static CrearEventoSolidarioProto aCrearProto(CrearEventoSolidarioDTO dto) {
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
    public static ModificarEventoSolidarioDTO aModificarDTO(ModificarEventoSolidarioProto proto) {
        if (proto == null) return null;

        ModificarEventoSolidarioDTO dto = new ModificarEventoSolidarioDTO();
        dto.setIdEventoSolidario(proto.getIdEventoSolidario());
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

    public static ModificarEventoSolidarioProto aModificarProto(ModificarEventoSolidarioDTO dto) {
        if (dto == null) return null;

        return ModificarEventoSolidarioProto.newBuilder()
                .setIdEventoSolidario(dto.getIdEventoSolidario())
                .setNombre(dto.getNombre())
                .setFechaHora(DateTimeMapper.aProto(dto.getFechaHora()))
                .addAllMiembros(
                        dto.getMiembros().stream()
                                .map(UsuarioMapper::aMiembroProto)
                                .toList()
                )
                .build();
    }

    // EventoSolidarioDTO <-> EventoSolidarioProto
    public static EventoSolidarioDTO toDTO(EventoSolidarioProto proto) {
        if (proto == null) return null;

        EventoSolidarioDTO dto = new EventoSolidarioDTO();

        dto.setIdEventoSolidario(proto.getIdEventoSolidario());
        dto.setNombre(proto.getNombre());
        dto.setDescripcion(proto.getDescripcion());
        dto.setFechaHora(DateTimeMapper.desdeProto(proto.getFechaHora()));
        dto.setMiembros(proto.getMiembrosList()
                .stream()
                .map(UsuarioMapper::aMiembroDTO)
                .toList()
        );
        dto.setVoluntariosExternos(proto.getVoluntariosExternosList()
                .stream()
                .map(VoluntarioExternoMapper::aDTO)
                .toList()
        );

        return dto;
    }

    public static EventoSolidarioProto toProto(EventoSolidarioDTO dto) {
        if (dto == null) return null;

        return EventoSolidarioProto.newBuilder()
                .setIdEventoSolidario(dto.getIdEventoSolidario())
                .setNombre(dto.getNombre())
                .setDescripcion(dto.getDescripcion())
                .setFechaHora(DateTimeMapper.aProto(dto.getFechaHora()))
                .addAllMiembros(
                        dto.getMiembros()
                                .stream()
                                .map(UsuarioMapper::aMiembroProto)
                                .toList()
                )
                .addAllVoluntariosExternos(
                        dto.getVoluntariosExternos()
                                .stream()
                                .map(VoluntarioExternoMapper::aProto)
                                .toList()
                )
                .build();
    }
}