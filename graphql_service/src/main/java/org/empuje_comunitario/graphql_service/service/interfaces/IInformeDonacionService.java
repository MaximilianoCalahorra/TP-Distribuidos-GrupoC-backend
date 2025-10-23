package org.empuje_comunitario.graphql_service.service.interfaces;

import java.util.List;

import org.empuje_comunitario.graphql_service.graphql.model.FiltroDonacionBusquedaInputGraphQL;
import org.empuje_comunitario.graphql_service.graphql.model.InformeDonacionGraphQL;

public interface IInformeDonacionService {
	///Obtener informe de las donaciones de la ONG agrupadas por categoría y eliminado:
	public List<InformeDonacionGraphQL> informeDonaciones(FiltroDonacionBusquedaInputGraphQL filtro);
}
