package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ModificarEventoSolidarioDTO {
	private Long idEventoSolidario;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaHora;
    private List<MiembroDTO> miembros;

    // Constructor con todos los campos
    public ModificarEventoSolidarioDTO(Long idEventoSolidario, String nombre, String descripcion, LocalDateTime fechaHora, List<MiembroDTO> miembros) {
    	this.idEventoSolidario = idEventoSolidario;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
        this.miembros = miembros;
    }
}