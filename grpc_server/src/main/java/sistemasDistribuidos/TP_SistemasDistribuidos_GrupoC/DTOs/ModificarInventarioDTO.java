package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ModificarInventarioDTO {
	private Long idInventario;
    private String descripcion;
    private int cantidad;

    // Constructor con todos los campos
    public ModificarInventarioDTO(Long idInventario, String descripcion, int cantidad) {
    	this.idInventario = idInventario;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }
}