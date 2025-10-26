package org.empuje_comunitario.rest_service.repository;

import org.empuje_comunitario.rest_service.model.FiltroEvento;
import org.empuje_comunitario.rest_service.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IFiltroEventoRepository extends JpaRepository<FiltroEvento, Long> {

    ///Buscar un filtro por nombre y usuario:
    Optional<FiltroEvento> findByNombreFiltroAndUsuario(String nombreFiltroEvento, Usuario usuario);

	///Listar todos los filtros de un usuario:
    List<FiltroEvento> findByUsuario(Usuario usuario);

}

