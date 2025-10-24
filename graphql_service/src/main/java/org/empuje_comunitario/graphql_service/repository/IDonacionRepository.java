package org.empuje_comunitario.graphql_service.repository;

import org.empuje_comunitario.graphql_service.model.Donacion;
import org.empuje_comunitario.graphql_service.model.EventoSolidario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDonacionRepository extends JpaRepository<Donacion, Long> {
    ///Devuelve si hay alguna donación en el evento:
	public boolean existsByEventoSolidario(EventoSolidario evento);
}

