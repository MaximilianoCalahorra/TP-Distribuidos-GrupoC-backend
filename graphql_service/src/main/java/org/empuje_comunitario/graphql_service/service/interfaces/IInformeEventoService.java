package org.empuje_comunitario.graphql_service.service.interfaces;

import java.util.List;

import org.empuje_comunitario.graphql_service.graphql.model.InformeEventoMesGraphQL;

public interface IInformeEventoService {
	///Obtener informe de participación en eventos de la ONG agrupados por mes:
	public List<InformeEventoMesGraphQL> informeParticipacionEventos(String filtro);
}
