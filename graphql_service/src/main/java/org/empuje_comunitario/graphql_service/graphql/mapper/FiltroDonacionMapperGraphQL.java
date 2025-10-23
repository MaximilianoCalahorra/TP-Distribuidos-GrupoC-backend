package org.empuje_comunitario.graphql_service.graphql.mapper;

import org.empuje_comunitario.graphql_service.dto.FiltroDonacionDTO;
import org.empuje_comunitario.graphql_service.graphql.model.FiltroDonacionGraphQL;
import org.empuje_comunitario.graphql_service.graphql.model.FiltroDonacionInputGraphQL;

public class FiltroDonacionMapperGraphQL {
	// =======================
    // DTOs <-> GraphQL
    // =======================
	
	// FiltroDonacionDTO <-> FiltroDonacionGraphQL
    public static FiltroDonacionDTO aDTO(FiltroDonacionGraphQL graphQL) {
        if (graphQL == null) return null;

        FiltroDonacionDTO dto = new FiltroDonacionDTO();
        
        dto.setIdFiltroDonacion(graphQL.getIdFiltroDonacion());
        dto.setNombreFiltro(graphQL.getNombreFiltro());
        dto.setFechaHoraAltaDesde(LocalDateTimeMapper.desdeString(graphQL.getFechaHoraAltaDesde()));
        dto.setFechaHoraAltaHasta(LocalDateTimeMapper.desdeString(graphQL.getFechaHoraAltaHasta()));
        dto.setEliminado(EstadoEliminadoMapper.aEnum(graphQL.getEliminado()));
        
        return dto;
    }

    public static FiltroDonacionGraphQL aGraphQL(FiltroDonacionDTO dto) {
        if (dto == null) return null;

        FiltroDonacionGraphQL graphQL = new FiltroDonacionGraphQL();
        
        graphQL.setIdFiltroDonacion(dto.getIdFiltroDonacion());
        graphQL.setNombreFiltro(dto.getNombreFiltro());
        graphQL.setFechaHoraAltaDesde(LocalDateTimeMapper.aString(dto.getFechaHoraAltaDesde()));
        graphQL.setFechaHoraAltaHasta(LocalDateTimeMapper.aString(dto.getFechaHoraAltaHasta()));
        graphQL.setEliminado(EstadoEliminadoMapper.aString(dto.getEliminado()));

        return graphQL;
    }
    
    // FiltroDonacionDTO <-> FiltroDonacionInputGraphQL
    public static FiltroDonacionDTO aDTO(FiltroDonacionInputGraphQL graphQL) {
    	if (graphQL == null) return null;
    	
    	FiltroDonacionDTO dto = new FiltroDonacionDTO();
    	
    	if (graphQL.getIdFiltroDonacion() != null && graphQL.getIdFiltroDonacion() > 0) {
    		dto.setIdFiltroDonacion(graphQL.getIdFiltroDonacion());
    	}
    	
    	dto.setNombreFiltro(graphQL.getNombreFiltro());
    	dto.setFechaHoraAltaDesde(LocalDateTimeMapper.desdeString(graphQL.getFechaHoraAltaDesde()));
    	dto.setFechaHoraAltaHasta(LocalDateTimeMapper.desdeString(graphQL.getFechaHoraAltaHasta()));
    	dto.setEliminado(EstadoEliminadoMapper.aEnum(graphQL.getEliminado()));
    	
    	return dto;
    }
    
    public static FiltroDonacionInputGraphQL aInputGraphQL(FiltroDonacionDTO dto) {
    	if (dto == null) return null;
    	
    	FiltroDonacionInputGraphQL inputGraphQL = new FiltroDonacionInputGraphQL();
    	
    	if (dto.getIdFiltroDonacion() != null && dto.getIdFiltroDonacion() > 0) {
    		inputGraphQL.setIdFiltroDonacion(dto.getIdFiltroDonacion());
    	}
    	
    	inputGraphQL.setNombreFiltro(dto.getNombreFiltro());
    	inputGraphQL.setFechaHoraAltaDesde(LocalDateTimeMapper.aString(dto.getFechaHoraAltaDesde()));
    	inputGraphQL.setFechaHoraAltaHasta(LocalDateTimeMapper.aString(dto.getFechaHoraAltaHasta()));
    	inputGraphQL.setEliminado(EstadoEliminadoMapper.aString(dto.getEliminado()));
    	
    	return inputGraphQL;
    }
}
