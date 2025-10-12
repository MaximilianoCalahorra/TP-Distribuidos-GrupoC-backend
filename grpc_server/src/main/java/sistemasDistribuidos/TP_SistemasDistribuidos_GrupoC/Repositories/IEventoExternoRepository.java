package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoExterno;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoSolidario;

@Repository
public interface IEventoExternoRepository extends JpaRepository<EventoExterno, Long> {
    Optional<EventoExterno> findByIdEventoOrigenAndIdOrganizacion(String idEventoOrigen, String idOrganizacion);
    @Query("SELECT DISTINCT e FROM EventoExterno e LEFT JOIN FETCH e.participantesInternos pi LEFT JOIN FETCH pi.rol")
    List<EventoExterno> listAllEvents ();
}
