package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransferenciaDonacionDTO {
	///Artibutos:
	private Long idTransferenciaDonacion;
	private String idSolicitudDonacion;
	private String idOrganizacionDonante;
	private String idOrganizacionReceptora;
	private LocalDateTime fechaHora;
	private List<ItemDonacionDTO> items;
	
	///Constructor:
	public TransferenciaDonacionDTO(Long idTransferenciaDonacion, String idSolicitudDonacion, String idOrganizacionDonante, String idOrganizacionReceptora, LocalDateTime fechaHora, List<ItemDonacionDTO> items) {
		this.idTransferenciaDonacion = idTransferenciaDonacion;
		this.idSolicitudDonacion = idSolicitudDonacion;
		this.idOrganizacionDonante = idOrganizacionDonante;
		this.idOrganizacionReceptora = idOrganizacionReceptora;
		this.fechaHora = fechaHora;
		this.items = items;
	}
}
