package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.Categoria;

@Getter
@Setter
@NoArgsConstructor
public class CrearInventarioDTO {
    private Categoria categoria;
    private String descripcion;
    private int cantidad;

    // Constructor con todos los campos
    public CrearInventarioDTO(Categoria categoria, String descripcion, int cantidad) {
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }
}