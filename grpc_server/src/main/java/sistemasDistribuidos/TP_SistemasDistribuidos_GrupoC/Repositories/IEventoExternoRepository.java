package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoExterno;

@Repository
public interface IEventoExternoRepository extends JpaRepository<EventoExterno, Long> {
    @Query("SELECT e FROM EventoExterno e LEFT JOIN FETCH e.participantesInternos pi LEFT JOIN FETCH pi.rol WHERE e.idEventoOrigen = :idEventoOrigen AND e.idOrganizacion = :idOrganizacion")
    Optional<EventoExterno> findByIdEventoOrigenAndIdOrganizacion(@Param("idEventoOrigen") String idEventoOrigen, @Param("idOrganizacion") String idOrganizacion);
    @Query("SELECT DISTINCT e FROM EventoExterno e LEFT JOIN FETCH e.participantesInternos pi LEFT JOIN FETCH pi.rol")
    List<EventoExterno> listAllEvents ();
}
