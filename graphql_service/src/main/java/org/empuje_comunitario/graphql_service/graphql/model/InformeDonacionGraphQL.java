package org.empuje_comunitario.graphql_service.graphql.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InformeDonacionGraphQL {
	///Atributos:
    private String categoria;
    private Boolean eliminado;
    private Integer cantidadTotal;
    
    ///Constructor:
    public InformeDonacionGraphQL(String categoria, Boolean eliminado, Integer cantidadTotal) {
    	this.categoria = categoria;
    	this.eliminado = eliminado;
    	this.cantidadTotal = cantidadTotal;
    }
}
