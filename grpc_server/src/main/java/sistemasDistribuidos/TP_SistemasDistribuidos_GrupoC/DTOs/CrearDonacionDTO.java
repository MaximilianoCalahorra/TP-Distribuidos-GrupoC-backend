package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.Categoria;

@Getter
@Setter
@NoArgsConstructor
public class CrearDonacionDTO {
    private Long idEvento;
    private Categoria categoria;
    private int cantidad;
    private String descripcion;

    // Constructor con todos los campos
    public CrearDonacionDTO(Long idEvento, Categoria categoria, int cantidad, String descripcion) {
        this.idEvento = idEvento;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
    }
}