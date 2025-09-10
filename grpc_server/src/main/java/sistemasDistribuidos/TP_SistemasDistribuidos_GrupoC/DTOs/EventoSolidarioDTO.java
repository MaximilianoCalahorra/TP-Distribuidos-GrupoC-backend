package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EventoSolidarioDTO {
	private Long idEventoSolidario;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaHora;

    // Constructor con todos los campos
    public EventoSolidarioDTO(Long idEventoSolidario, String nombre, String descripcion, LocalDateTime fechaHora) {
    	this.idEventoSolidario = idEventoSolidario;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
    }
}