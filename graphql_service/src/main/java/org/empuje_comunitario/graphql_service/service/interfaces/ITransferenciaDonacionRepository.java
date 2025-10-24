package org.empuje_comunitario.graphql_service.service.interfaces;

import java.util.List;

import org.empuje_comunitario.graphql_service.model.TransferenciaDonacion;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransferenciaDonacionRepository extends JpaRepository<TransferenciaDonacion, Long> {
    //Consulta dinámica con filtros opcionales usando Spring Data JPA Specification:
    public List<TransferenciaDonacion> findAll(Specification<TransferenciaDonacion> spec);
}

