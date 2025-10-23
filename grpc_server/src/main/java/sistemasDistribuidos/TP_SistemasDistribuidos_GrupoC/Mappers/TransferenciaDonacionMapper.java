package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import java.util.stream.Collectors;

import proto.dtos.transferencia_donacion.TransferenciaDonacionProto;
import proto.services.kafka.PublicacionTransferenciaDonacionKafkaProto;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.TransferenciaDonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.TransferenciaDonacion;

public class TransferenciaDonacionMapper {
	// =======================
    // Entidad <-> DTOs
    // =======================
	
	// TransferenciaDonacionDTO <-> TransferenciaDonacion
    public static TransferenciaDonacionDTO aDTO(TransferenciaDonacion entidad) {
        if (entidad == null) return null;

        TransferenciaDonacionDTO dto = new TransferenciaDonacionDTO();
        
        dto.setIdTransferenciaDonacion(entidad.getIdTransferenciaDonacion());
        dto.setIdSolicitudDonacion(entidad.getIdSolicitudDonacion());
        dto.setIdOrganizacionDonante(entidad.getIdOrganizacionDonante());
        dto.setIdOrganizacionReceptora(entidad.getIdOrganizacionReceptora());
        dto.setFechaHora(entidad.getFechaHora());
        dto.setUsuarioAlta(UsuarioMapper.aMiembroDTO(entidad.getUsuarioAlta()));
        dto.setUsuarioModificacion(UsuarioMapper.aMiembroDTO(entidad.getUsuarioModificacion()));
        dto.setEliminado(entidad.isEliminado());
        dto.setItems(
                entidad.getItems().stream()
                        .map(ItemDonacionMapper::aDTO)
                        .collect(Collectors.toList()));
        
        return dto;
    }

    public static TransferenciaDonacion aEntidad(TransferenciaDonacionDTO dto) {
        if (dto == null) return null;

        TransferenciaDonacion entidad = new TransferenciaDonacion();
        
        if (dto.getIdTransferenciaDonacion() != null && dto.getIdTransferenciaDonacion() > 0) {
        	entidad.setIdTransferenciaDonacion(dto.getIdTransferenciaDonacion());
        }
        
        entidad.setIdSolicitudDonacion(dto.getIdSolicitudDonacion());
        entidad.setIdSolicitudDonacion(dto.getIdSolicitudDonacion());
        entidad.setIdOrganizacionDonante(dto.getIdOrganizacionDonante());
        entidad.setIdOrganizacionReceptora(dto.getIdOrganizacionReceptora());
        entidad.setFechaHora(dto.getFechaHora());
        entidad.setItems(
                dto.getItems().stream()
                        .map(ItemDonacionMapper::aEntidad)
                        .collect(Collectors.toList()));

        return entidad;
    }
    
    // =======================
    // DTOs <-> Protos
    // =======================
	
	// TransferenciaDonacionDTO <-> TransferenciaDonacionProto
    public static TransferenciaDonacionDTO aDTO(TransferenciaDonacionProto proto) {
        if (proto == null) return null;

        TransferenciaDonacionDTO dto = new TransferenciaDonacionDTO();
        
        dto.setIdTransferenciaDonacion(proto.getIdTransferenciaDonacion());
        dto.setIdSolicitudDonacion(proto.getIdSolicitudDonacion());
        dto.setIdOrganizacionDonante(proto.getIdOrganizacionDonante());
        dto.setIdOrganizacionReceptora(proto.getIdOrganizacionReceptora());
        dto.setFechaHora(DateTimeMapper.desdeProto(proto.getFechaHora()));
        dto.setItems(
                proto.getItemsList().stream()
                        .map(ItemDonacionMapper::aDTO)
                        .collect(Collectors.toList()));
        
        return dto;
    }

    public static TransferenciaDonacionProto aProto(TransferenciaDonacionDTO dto) {
        if (dto == null) return null;

        return TransferenciaDonacionProto.newBuilder()
                .setIdTransferenciaDonacion(dto.getIdTransferenciaDonacion())
                .setIdSolicitudDonacion(dto.getIdSolicitudDonacion())
                .setIdOrganizacionDonante(dto.getIdOrganizacionDonante())
                .setIdOrganizacionReceptora(dto.getIdOrganizacionReceptora())
                .setFechaHora(DateTimeMapper.aProto(dto.getFechaHora()))
                .addAllItems(
                        dto.getItems().stream()
                                .map(ItemDonacionMapper::aProto)
                                .toList()
                )
                .build();
    }
    
    // PublicacionTransferenciaDonacionKafkaProto <-> TransferenciaDonacionDTO
    public static TransferenciaDonacionDTO aDTO(PublicacionTransferenciaDonacionKafkaProto proto) {
    	if (proto == null) return null;

        TransferenciaDonacionDTO dto = new TransferenciaDonacionDTO();
        
        dto.setIdSolicitudDonacion(proto.getIdSolicitud());
        dto.setIdOrganizacionDonante(proto.getIdOrganizacionDonante());
        dto.setItems(
                proto.getDonacionesList().stream()
                        .map(ItemDonacionMapper::aDTO)
                        .collect(Collectors.toList()));
        
        return dto;
    }
}
