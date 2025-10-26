package org.empuje_comunitario.rest_service.repository;

import org.empuje_comunitario.rest_service.model.InformeDonacionView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface IInformeDonacionViewRepository extends JpaRepository <InformeDonacionView, Long> {
    @Query("select v from InformeDonacionView v order by v.categoria asc, v.fechaHora asc")
    List<InformeDonacionView> findAllOrdered();
}
