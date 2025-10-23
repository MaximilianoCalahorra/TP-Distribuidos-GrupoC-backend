package org.empuje_comunitario.graphql_service.graphql.model;

import org.empuje_comunitario.graphql_service.enums.Categoria;
import org.empuje_comunitario.graphql_service.enums.EstadoEliminado;
import org.empuje_comunitario.graphql_service.enums.TipoTransferencia;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FiltroDonacionBusquedaInputGraphQL {
	///Atributos:
    private Categoria categoria;
    private String fechaHoraAltaDesde;
    private String fechaHoraAltaHasta;
    private EstadoEliminado eliminado;
    private TipoTransferencia tipoTransferencia;
    
    ///Constructor:
    public FiltroDonacionBusquedaInputGraphQL(Categoria categoria, String fechaHoraAltaDesde, String fechaHoraAltaHasta, EstadoEliminado eliminado, 
    										  TipoTransferencia tipoTransferencia) {
    	this.categoria = categoria;
    	this.fechaHoraAltaDesde = fechaHoraAltaDesde;
    	this.fechaHoraAltaHasta = fechaHoraAltaHasta;
    	this.eliminado = eliminado;
    	this.tipoTransferencia = tipoTransferencia;
    }
}
