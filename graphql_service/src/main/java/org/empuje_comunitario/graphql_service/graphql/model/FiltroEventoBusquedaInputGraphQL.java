package org.empuje_comunitario.graphql_service.graphql.model;

import org.empuje_comunitario.graphql_service.enums.RepartoDonacion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FiltroEventoBusquedaInputGraphQL {
	///Atributos:
	private String usuarioEmail;
	private String fechaHoraDesde;
	private String fechaHoraHasta;
	private RepartoDonacion repartoDonaciones;
	
	///Constructor:
	public FiltroEventoBusquedaInputGraphQL(String usuarioEmail, String fechaHoraDesde, String fechaHoraHasta, RepartoDonacion repartoDonaciones) {
		this.usuarioEmail = usuarioEmail;
		this.fechaHoraDesde = fechaHoraDesde;
		this.fechaHoraHasta = fechaHoraHasta;
		this.repartoDonaciones = repartoDonaciones;
	}
}
