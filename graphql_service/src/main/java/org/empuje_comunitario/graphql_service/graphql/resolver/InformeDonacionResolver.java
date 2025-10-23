package org.empuje_comunitario.graphql_service.graphql.resolver;

import java.util.List;

import org.empuje_comunitario.graphql_service.graphql.model.FiltroDonacionBusquedaInputGraphQL;
import org.empuje_comunitario.graphql_service.graphql.model.InformeDonacionGraphQL;
import org.empuje_comunitario.graphql_service.service.interfaces.IInformeDonacionService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@PreAuthorize("hasRole('PRESIDENTE') or hasRole('VOCAL')")
@RequiredArgsConstructor
public class InformeDonacionResolver {
	///Atributo:
    private final IInformeDonacionService informeDonacionService;

    ///Resolvers:
    
    //Obtener informes de donaciones de la ONG agrupados por categoría y eliminado:
    @QueryMapping("informeDonacionesPorCategoria")
    public List<InformeDonacionGraphQL> informeDonaciones(@Argument("filtro") FiltroDonacionBusquedaInputGraphQL filtro) {
        return informeDonacionService.informeDonaciones(filtro);
    }
}
