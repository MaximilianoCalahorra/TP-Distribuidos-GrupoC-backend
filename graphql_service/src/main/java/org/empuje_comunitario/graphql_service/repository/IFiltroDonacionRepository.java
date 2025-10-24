package org.empuje_comunitario.graphql_service.repository;

import java.util.List;
import java.util.Optional;

import org.empuje_comunitario.graphql_service.model.FiltroDonacion;
import org.empuje_comunitario.graphql_service.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFiltroDonacionRepository extends JpaRepository<FiltroDonacion, Long> {
	///Listar todos los filtros de un usuario:
    List<FiltroDonacion> findByUsuario(Usuario usuario);

    ///Buscar un filtro por nombre y usuario:
    Optional<FiltroDonacion> findByNombreFiltroAndUsuario(String nombreFiltro, Usuario usuario);
}
