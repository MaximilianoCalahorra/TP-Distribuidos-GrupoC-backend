package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import java.util.stream.Collectors;

import proto.dtos.solicitud_donacion.SolicitudDonacionProto;
import proto.services.kafka.PublicacionSolicitudDonacionKafkaProto;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.SolicitudDonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.SolicitudDonacion;

public class SolicitudDonacionMapper {
	// =======================
    // Entidad <-> DTOs
    // =======================
	
	// SolicitudDonacionDTO <-> SolicitudDonacion
    public static SolicitudDonacionDTO aDTO(SolicitudDonacion entidad) {
        if (entidad == null) return null;

        SolicitudDonacionDTO dto = new SolicitudDonacionDTO();
        
        dto.setIdSolicitudDonacion(entidad.getIdSolicitudDonacion());
        dto.setIdSolicitudDonacionOrigen(entidad.getIdSolicitudDonacionOrigen());
        dto.setIdOrganizacion(entidad.getIdOrganizacion());
        dto.setItems(
                entidad.getItems().stream()
                        .map(ItemDonacionMapper::aDTO)
                        .collect(Collectors.toList()));
        
        return dto;
    }

    public static SolicitudDonacion aEntidad(SolicitudDonacionDTO dto) {
        if (dto == null) return null;

        SolicitudDonacion entidad = new SolicitudDonacion();
        
        if (dto.getIdSolicitudDonacionOrigen() != null && !dto.getIdSolicitudDonacionOrigen().isEmpty()) {
        	entidad.setIdSolicitudDonacionOrigen(dto.getIdSolicitudDonacionOrigen());
        }
        
        entidad.setIdOrganizacion(dto.getIdOrganizacion());
        entidad.setItems(
                dto.getItems().stream()
                        .map(ItemDonacionMapper::aEntidad)
                        .collect(Collectors.toList()));

        return entidad;
    }
    
    // =======================
    // DTOs <-> Protos
    // =======================
	
	// SolicitudDonacionDTO <-> SolicitudDonacionProto
    public static SolicitudDonacionDTO aDTO(SolicitudDonacionProto proto) {
        if (proto == null) return null;

        SolicitudDonacionDTO dto = new SolicitudDonacionDTO();
        
        dto.setIdSolicitudDonacion(proto.getIdSolicitudDonacion());
        dto.setIdSolicitudDonacionOrigen(proto.getIdSolicitudDonacionOrigen());
        dto.setIdOrganizacion(proto.getIdOrganizacion());
        dto.setItems(
                proto.getItemsList().stream()
                        .map(ItemDonacionMapper::aDTO)
                        .collect(Collectors.toList()));
        
        return dto;
    }

    public static SolicitudDonacionProto aProto(SolicitudDonacionDTO dto) {
        if (dto == null) return null;

        return SolicitudDonacionProto.newBuilder()
                .setIdSolicitudDonacion(dto.getIdSolicitudDonacion())
                .setIdSolicitudDonacionOrigen(dto.getIdSolicitudDonacionOrigen())
                .setIdOrganizacion(dto.getIdOrganizacion())
                .addAllItems(
                        dto.getItems().stream()
                                .map(ItemDonacionMapper::aProto)
                                .toList()
                )
                .build();
    }
    
    // PublicacionSolicitudDonacionKafkaProto <-> SolicitudDonacionDTO
    public static SolicitudDonacionDTO aDTO(PublicacionSolicitudDonacionKafkaProto proto) {
    	if (proto == null) return null;

        SolicitudDonacionDTO dto = new SolicitudDonacionDTO();
        
        dto.setIdSolicitudDonacionOrigen(proto.getIdSolicitud());
        dto.setIdOrganizacion(proto.getIdOrganizacion());
        dto.setItems(
                proto.getDonacionesList().stream()
                        .map(ItemDonacionMapper::aDTO)
                        .collect(Collectors.toList()));
        
        return dto;
    }
}
