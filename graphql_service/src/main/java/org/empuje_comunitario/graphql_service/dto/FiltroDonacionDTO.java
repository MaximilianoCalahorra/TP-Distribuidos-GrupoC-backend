package org.empuje_comunitario.graphql_service.dto;

import java.time.LocalDateTime;

import org.empuje_comunitario.graphql_service.enums.EstadoEliminado;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FiltroDonacionDTO {
	///Atributos:
	private Long idFiltroDonacion;
	private String nombreFiltro;
	private LocalDateTime fechaHoraAltaDesde;
	private LocalDateTime fechaHoraAltaHasta;
	private EstadoEliminado eliminado;
	private MiembroDTO usuario;
	
	///Constructor:
	public FiltroDonacionDTO(Long idFiltroDonacion, String nombreFiltro, LocalDateTime fechaHoraAltaDesde, LocalDateTime fechaHoraAltaHasta, EstadoEliminado eliminado, MiembroDTO usuario) {
		this.idFiltroDonacion = idFiltroDonacion;
		this.nombreFiltro = nombreFiltro;
		this.fechaHoraAltaDesde = fechaHoraAltaDesde;
		this.fechaHoraAltaHasta = fechaHoraAltaHasta;
		this.eliminado = eliminado;
		this.usuario = usuario;
	}
}
