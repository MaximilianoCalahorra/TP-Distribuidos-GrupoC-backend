package org.empuje_comunitario.graphql_service.graphql.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InformeEventoMesGraphQL {
	///Atributos:
    private Integer mes;
    private List<InformeEventoParticipacionGraphQL> eventos;
    
    ///Constructor:
    public InformeEventoMesGraphQL(Integer mes, List<InformeEventoParticipacionGraphQL> eventos) {
    	this.mes = mes;
    	this.eventos = eventos;
    }
}
