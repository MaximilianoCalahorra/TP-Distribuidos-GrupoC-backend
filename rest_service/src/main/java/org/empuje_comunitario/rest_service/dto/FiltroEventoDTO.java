package org.empuje_comunitario.rest_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import org.empuje_comunitario.rest_service.model.Usuario;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltroEventoDTO {

    private Long id;
    private String nombreFiltro;
    private String emailUsuario; 
    private LocalDateTime fechaHoraDesde;
    private LocalDateTime fechaHoraHasta;
    private String repartoDonaciones;
    private Usuario usuario;

}

