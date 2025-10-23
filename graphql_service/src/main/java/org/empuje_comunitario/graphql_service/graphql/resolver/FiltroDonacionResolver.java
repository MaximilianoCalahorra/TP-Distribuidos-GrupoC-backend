package org.empuje_comunitario.graphql_service.graphql.resolver;

import java.util.List;

import org.empuje_comunitario.graphql_service.graphql.model.FiltroDonacionGraphQL;
import org.empuje_comunitario.graphql_service.graphql.model.FiltroDonacionInputGraphQL;
import org.empuje_comunitario.graphql_service.service.interfaces.IFiltroDonacionService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@PreAuthorize("hasRole('PRESIDENTE') or hasRole('VOCAL')")
@RequiredArgsConstructor
public class FiltroDonacionResolver {
	///Atributo:
    private final IFiltroDonacionService filtroDonacionService;

    ///Resolvers:
    
    //Obtener filtros de donación del usuario logueado:
    @QueryMapping("filtrosDonacionUsuario")
    public List<FiltroDonacionGraphQL> filtrosDonacionUsuario() {
        return filtroDonacionService.filtrosUsuarioLogueado();
    }

    //Crear filtro de donación para el usuario logueado:
    @MutationMapping("crearFiltroDonacion")
    public FiltroDonacionGraphQL crearFiltroDonacion(@Argument("input") FiltroDonacionInputGraphQL input) {
        return filtroDonacionService.crearFiltro(input);
    }
    
    //Modificar filtro de donación del usuario logueado:
    @MutationMapping("modificarFiltroDonacion")
    public FiltroDonacionGraphQL modificarFiltroDonacion(@Argument("input") FiltroDonacionInputGraphQL input) {
        return filtroDonacionService.modificarFiltro(input);
    }

    //Eliminar filtro de donación del usuario logueado:
    @MutationMapping("eliminarFiltroDonacion")
    public Boolean eliminarFiltroDonacion(@Argument("idFiltroDonacion") Long idFiltroDonacion) {
        return filtroDonacionService.eliminarFiltro(idFiltroDonacion);
    }
}
