package org.empuje_comunitario.graphql_service.repository;

import java.util.List;

import org.empuje_comunitario.graphql_service.model.EventoSolidario;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventoRepository extends JpaRepository<EventoSolidario, Long> {
    //Consulta dinámica con filtros opcionales usando Spring Data JPA Specification:
    public List<EventoSolidario> findAll(Specification<EventoSolidario> spec);
}

