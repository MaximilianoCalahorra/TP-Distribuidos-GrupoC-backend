package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OfertaDonacionDTO {
    /// atributos
    private String idOferta;
    private String idOrganizacion;
    private List<ItemDonacionDTO> items;

    ///Constructor:
    public OfertaDonacionDTO(String idOferta, String idOrganizacion, List<ItemDonacionDTO> items) {
        this.idOferta = idOferta;
        this.idOrganizacion = idOrganizacion;
        this.items = items;
    }
}
