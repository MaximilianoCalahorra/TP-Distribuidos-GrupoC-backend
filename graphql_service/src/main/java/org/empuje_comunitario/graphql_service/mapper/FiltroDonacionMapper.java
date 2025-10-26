package org.empuje_comunitario.graphql_service.mapper;

import org.empuje_comunitario.graphql_service.dto.FiltroDonacionDTO;
import org.empuje_comunitario.graphql_service.model.FiltroDonacion;

public class FiltroDonacionMapper {
	// =======================
    // Entidad <-> DTOs
    // =======================
	
	// FiltroDonacionDTO <-> FiltroDonacion
    public static FiltroDonacionDTO aDTO(FiltroDonacion entidad) {
        if (entidad == null) return null;

        FiltroDonacionDTO dto = new FiltroDonacionDTO();
        
        dto.setIdFiltroDonacion(entidad.getIdFiltroDonacion());
        dto.setCategoria(entidad.getCategoria());
        dto.setNombreFiltro(entidad.getNombreFiltro());
        dto.setFechaHoraAltaDesde(entidad.getFechaHoraAltaDesde());
        dto.setFechaHoraAltaHasta(entidad.getFechaHoraAltaHasta());
        dto.setEliminado(entidad.getEliminado());
        dto.setUsuario(UsuarioMapper.aMiembroDTO(entidad.getUsuario()));
        
        return dto;
    }

    public static FiltroDonacion aEntidad(FiltroDonacionDTO dto) {
        if (dto == null) return null;

        FiltroDonacion entidad = new FiltroDonacion();
        
        if (dto.getIdFiltroDonacion() != null && dto.getIdFiltroDonacion() > 0) {
        	entidad.setIdFiltroDonacion(dto.getIdFiltroDonacion());
        }
        entidad.setCategoria(dto.getCategoria());
        entidad.setNombreFiltro(dto.getNombreFiltro());
        entidad.setFechaHoraAltaDesde(dto.getFechaHoraAltaDesde());
        entidad.setFechaHoraAltaHasta(dto.getFechaHoraAltaHasta());
        entidad.setEliminado(dto.getEliminado());
        entidad.setUsuario(UsuarioMapper.aEntidad(dto.getUsuario()));

        return entidad;
    }
}
