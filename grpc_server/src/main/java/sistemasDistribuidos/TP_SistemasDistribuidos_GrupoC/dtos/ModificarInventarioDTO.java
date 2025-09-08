package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ModificarInventarioDTO {
    private String descripcion;
    private int cantidad;

    // Constructor con todos los campos
    public ModificarInventarioDTO(String descripcion, int cantidad) {
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }
}