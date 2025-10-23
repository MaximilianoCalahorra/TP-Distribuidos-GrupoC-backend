package org.empuje_comunitario.graphql_service.service.interfaces;

import java.util.List;
import java.util.Optional;

import org.empuje_comunitario.graphql_service.graphql.model.FiltroDonacionGraphQL;
import org.empuje_comunitario.graphql_service.graphql.model.FiltroDonacionInputGraphQL;
import org.empuje_comunitario.graphql_service.model.FiltroDonacion;
import org.empuje_comunitario.graphql_service.model.Usuario;

public interface IFiltroDonacionService {
	///Listar todos los filtros guardados por un usuario:
    public List<FiltroDonacionGraphQL> filtrosUsuarioLogueado();

    ///Buscar un filtro por nombre e id de usuario:
    public Optional<FiltroDonacion> filtroPorNombreYUsuario(String nombreFiltroDonacion, Usuario usuario);
    
    ///Crear filtro:
    public FiltroDonacionGraphQL crearFiltro(FiltroDonacionInputGraphQL filtroDonacion);
    
    ///Modificar filtro:
    public FiltroDonacionGraphQL modificarFiltro(FiltroDonacionInputGraphQL filtroDonacion);
    
    ///Eliminar filtro:
    public boolean eliminarFiltro(Long idFiltroDonacion);
}
