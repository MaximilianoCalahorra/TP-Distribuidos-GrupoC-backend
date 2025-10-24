package org.empuje_comunitario.graphql_service.graphql.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InformeEventoParticipacionGraphQL {
	///Atributos:
    private String dia;
    private String nombreEvento;
    private String descripcion;
    private Boolean repartoDonaciones;
    
    ///Constructor:
    public InformeEventoParticipacionGraphQL(String dia, String nombreEvento, String descripcion, Boolean repartoDonaciones) {
    	this.dia = dia;
    	this.nombreEvento = nombreEvento;
    	this.descripcion = descripcion;
    	this.repartoDonaciones = repartoDonaciones;
    }
}
