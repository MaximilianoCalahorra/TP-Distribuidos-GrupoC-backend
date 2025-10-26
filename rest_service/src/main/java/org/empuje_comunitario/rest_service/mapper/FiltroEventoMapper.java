package org.empuje_comunitario.rest_service.mapper;

import org.empuje_comunitario.rest_service.dto.FiltroEventoDTO;
import org.empuje_comunitario.rest_service.model.FiltroEvento;
import org.springframework.stereotype.Component;

@Component
public class FiltroEventoMapper {

    public static FiltroEvento aEntidad(FiltroEventoDTO dto) {
        FiltroEvento entity = new FiltroEvento();
        
        // El ID solo se setea si es una actualización
        if (dto.getId() != null) {
             entity.setId(dto.getId());
        }
        
        // Criterios y nombre del filtro
        entity.setNombreFiltro(dto.getNombreFiltro()); 
        entity.setFechaHoraDesde(dto.getFechaHoraDesde());
        entity.setFechaHoraHasta(dto.getFechaHoraHasta());
        entity.setRepartoDonaciones(dto.getRepartoDonaciones());
        
        return entity;
    }

    public static FiltroEventoDTO aDTO(FiltroEvento entity) {
        FiltroEventoDTO dto = new FiltroEventoDTO();
        dto.setId(entity.getId());
        dto.setNombreFiltro(entity.getNombreFiltro());
        dto.setEmailUsuario(entity.getEmailUsuario());
        dto.setFechaHoraDesde(entity.getFechaHoraDesde());
        dto.setFechaHoraHasta(entity.getFechaHoraHasta());
        dto.setRepartoDonaciones(entity.getRepartoDonaciones());

        dto.setUsuario(entity.getUsuario());
        return dto;
    }
}
