package org.empuje_comunitario.rest_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import org.empuje_comunitario.rest_service.enums.RepartoDonacion;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltroEventoDTO {

    private Long id;
    private String nombreFiltro;
    private String emailUsuario; 
    private LocalDateTime fechaHoraDesde;
    private LocalDateTime fechaHoraHasta;
    private RepartoDonacion repartoDonaciones;

}

