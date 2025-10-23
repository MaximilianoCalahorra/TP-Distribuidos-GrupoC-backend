package org.empuje_comunitario.graphql_service.mapper;

import java.util.stream.Collectors;

import org.empuje_comunitario.graphql_service.dto.TransferenciaDonacionDTO;
import org.empuje_comunitario.graphql_service.model.TransferenciaDonacion;

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
}
