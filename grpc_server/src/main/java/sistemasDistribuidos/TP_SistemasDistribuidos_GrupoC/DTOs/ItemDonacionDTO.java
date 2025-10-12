package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.Categoria;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.Operacion;

@Getter
@Setter
@NoArgsConstructor
public class ItemDonacionDTO {
	///Atributos:
	private Long idItemDonacion;
	private Categoria categoria;
	private String descripcion;
	private int cantidad;
	private Operacion operacion;
	
	///Constructor:
	public ItemDonacionDTO(Long idItemDonacion, Categoria categoria, String descripcion, int cantidad, Operacion operacion) {
		this.idItemDonacion = idItemDonacion;
		this.categoria = categoria;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
		this.operacion = operacion;
	}
}
