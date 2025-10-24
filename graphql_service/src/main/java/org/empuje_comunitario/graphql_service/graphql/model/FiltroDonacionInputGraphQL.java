package org.empuje_comunitario.graphql_service.graphql.model;

import org.empuje_comunitario.graphql_service.enums.Categoria;
import org.empuje_comunitario.graphql_service.enums.EstadoEliminado;

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
    private Categoria categoria;
    private String fechaHoraAltaDesde;
    private String fechaHoraAltaHasta;
    private EstadoEliminado eliminado;
    
    ///Constructor:
    public FiltroDonacionInputGraphQL(Long idFiltroDonacion, Categoria categoria, String nombreFiltro, String fechaHoraAltaDesde, String fechaHoraAltaHasta, EstadoEliminado eliminado) {
    	this.idFiltroDonacion = idFiltroDonacion;
    	this.nombreFiltro = nombreFiltro;
    	this.categoria = categoria;
    	this.fechaHoraAltaDesde = fechaHoraAltaDesde;
    	this.fechaHoraAltaHasta = fechaHoraAltaHasta;
    	this.eliminado = eliminado;
    }
}
