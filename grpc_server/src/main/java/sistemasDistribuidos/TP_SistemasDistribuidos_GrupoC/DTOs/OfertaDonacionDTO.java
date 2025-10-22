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
    private Long idOfertaDonacion;
    private String idOfertaDonacionOrigen;
    private String idOrganizacion;
    private List<ItemDonacionDTO> items;

    ///Constructor:
    public OfertaDonacionDTO(Long idOfertaDonacion, String idOfertaDonacionOrigen, String idOrganizacion, List<ItemDonacionDTO> items) {
        this.idOfertaDonacion = idOfertaDonacion;
        this.idOfertaDonacionOrigen = idOfertaDonacionOrigen;
        this.idOrganizacion = idOrganizacion;
        this.items = items;
    }
}