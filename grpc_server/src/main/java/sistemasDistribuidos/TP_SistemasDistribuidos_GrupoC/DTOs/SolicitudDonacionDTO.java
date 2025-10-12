package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SolicitudDonacionDTO {
	///Artibutos:
	private Long idSolicitudDonacion;
	private String idSolicitudDonacionOrigen;
	private String idOrganizacion;
	private List<ItemDonacionDTO> items;
	
	///Constructor:
	public SolicitudDonacionDTO(Long idSolicitudDonacion, String idSolicitudDonacionOrigen, String idOrganizacion, List<ItemDonacionDTO> items) {
		this.idSolicitudDonacion = idSolicitudDonacion;
		this.idSolicitudDonacionOrigen = idSolicitudDonacionOrigen;
		this.idOrganizacion = idOrganizacion;
		this.items = items;
	}
}
