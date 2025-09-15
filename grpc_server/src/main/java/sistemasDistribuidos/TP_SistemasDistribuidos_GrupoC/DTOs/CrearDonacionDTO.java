package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.Categoria;

@Getter
@Setter
@NoArgsConstructor
public class CrearDonacionDTO {
    private Long idEventoSolidario;
    private int cantidad;
    private Long idInventario;

    // Constructor con todos los campos
    public CrearDonacionDTO(Long idEventoSolidario, Categoria categoria, int cantidad, String descripcion, Long idInventario) {
        this.idEventoSolidario = idEventoSolidario;
        this.cantidad = cantidad;
        this.idInventario = idInventario;
    }
}