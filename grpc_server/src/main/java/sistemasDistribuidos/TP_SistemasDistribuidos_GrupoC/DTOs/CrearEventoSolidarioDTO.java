package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CrearEventoSolidarioDTO {
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaHora;
    private List<MiembroDTO> miembros;

    // Constructor con todos los campos
    public CrearEventoSolidarioDTO(String nombre, String descripcion,
                                   LocalDateTime fechaHora, List<MiembroDTO> miembros) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
        this.miembros = miembros;
    }
}