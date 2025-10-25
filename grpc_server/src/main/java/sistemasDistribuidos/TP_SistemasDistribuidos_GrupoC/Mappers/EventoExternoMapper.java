package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import java.util.stream.Collectors;

import proto.dtos.evento_externo.EventoExternoProto;
import proto.services.kafka.PublicacionEventoKafkaProto;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.EventoExternoDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoExterno;

public class EventoExternoMapper {

    // =======================
    // Entidad <-> DTOs
    // =======================

    // EventoExternoDTO <-> EventoExterno
    public static EventoExternoDTO aDTO(EventoExterno entidad) {
        if (entidad == null) return null;

        EventoExternoDTO dto = new EventoExternoDTO();

        dto.setIdEventoOrigen(entidad.getIdEventoOrigen());
        if (entidad.getIdEventoExterno() != null && entidad.getIdEventoExterno() > 0) {
            dto.setIdEventoExterno(entidad.getIdEventoExterno());
        }
        dto.setNombre(entidad.getNombre());
        dto.setDescripcion(entidad.getDescripcion());
        dto.setFechaHora(entidad.getFechaHora());
        dto.setIdOrganizacion(entidad.getIdOrganizacion());
        dto.setParticipantesInternos(
                entidad.getParticipantesInternos().stream()
                        .map(UsuarioMapper::aMiembroDTO)
                        .collect(Collectors.toList()));
        return dto;
    }

    public static EventoExterno aEntidad(EventoExternoDTO dto) {
        if (dto == null) return null;

        EventoExterno entidad = new EventoExterno();

        if (dto.getIdEventoExterno() != null && dto.getIdEventoExterno() > 0) {
            entidad.setIdEventoExterno(dto.getIdEventoExterno());
        }

        if (dto.getIdEventoOrigen() != null && !dto.getIdEventoOrigen().isEmpty()) {
            entidad.setIdEventoOrigen(dto.getIdEventoOrigen());
        }

        entidad.setNombre(dto.getNombre());
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setFechaHora(dto.getFechaHora());
        entidad.setIdOrganizacion(dto.getIdOrganizacion());

        return entidad;
    }

    // =======================
    // DTOs <-> Protos
    // =======================

    // EventoExternoDTO <-> EventoExternoProto
    public static EventoExternoDTO aDTO(EventoExternoProto proto) {
        if (proto == null) return null;

        EventoExternoDTO dto = new EventoExternoDTO();

        if (proto.getIdEventoExterno() != 0) {
            dto.setIdEventoExterno(proto.getIdEventoExterno());
        }

        dto.setNombre(proto.getNombre());
        dto.setDescripcion(proto.getDescripcion());
        dto.setFechaHora(DateTimeMapper.desdeProto(proto.getFechaHora()));
        dto.setIdOrganizacion(proto.getIdOrganizacion());
        dto.setParticipantesInternos(proto.getParticipantesInternosList()
                .stream()
                .map(UsuarioMapper::aMiembroDTO)
                .toList()
        );

        return dto;
    }

    public static EventoExternoProto aProto(EventoExternoDTO dto) {
        if (dto == null) return null;

        return EventoExternoProto.newBuilder()
                .setIdEventoOrigen(Long.parseLong(dto.getIdEventoOrigen()))
                .setIdEventoExterno(dto.getIdEventoExterno() != null ? dto.getIdEventoExterno() : 0)
                .setNombre(dto.getNombre())
                .setDescripcion(dto.getDescripcion())
                .setFechaHora(DateTimeMapper.aProto(dto.getFechaHora()))
                .setIdOrganizacion(dto.getIdOrganizacion())
                .addAllParticipantesInternos(
                        dto.getParticipantesInternos()
                                .stream()
                                .map(UsuarioMapper::aMiembroProto)
                                .toList()
                )
                .build();
    }

    // EventoExternoDTO <-> PublicacionEventoKafkaProto

    public static EventoExternoDTO aDTO(PublicacionEventoKafkaProto proto) {
        if (proto == null) return null;

        EventoExternoDTO dto = new EventoExternoDTO();

        dto.setIdEventoOrigen(proto.getIdEvento());
        dto.setNombre(proto.getNombre());
        dto.setDescripcion(proto.getDescripcion());
        dto.setFechaHora(DateTimeMapper.desdeString(proto.getFechaHora()));
        dto.setIdOrganizacion(proto.getIdOrganizacion());

        return dto;
    }
}
