package org.empuje_comunitario.graphql_service.graphql.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FiltroDonacionInputGraphQL {
	///Atributos:
	private Long idFiltroDonacion;
    private String nombreFiltro;
    private String fechaHoraAltaDesde;
    private String fechaHoraAltaHasta;
    private String eliminado;
    
    ///Constructor:
    public FiltroDonacionInputGraphQL(Long idFiltroDonacion, String nombreFiltro, String fechaHoraAltaDesde, String fechaHoraAltaHasta, String eliminado) {
    	this.idFiltroDonacion = idFiltroDonacion;
    	this.nombreFiltro = nombreFiltro;
    	this.fechaHoraAltaDesde = fechaHoraAltaDesde;
    	this.fechaHoraAltaHasta = fechaHoraAltaHasta;
    	this.eliminado = eliminado;
    }
}
