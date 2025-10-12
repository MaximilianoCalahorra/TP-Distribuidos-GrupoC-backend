package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoSolidario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.VoluntarioExterno;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository("eventoSolidarioRepository")
public interface IEventoSolidarioRepository extends JpaRepository<EventoSolidario, Long> {
    @Query("SELECT DISTINCT e FROM EventoSolidario e LEFT JOIN FETCH e.miembros m LEFT JOIN FETCH m.rol")
    List<EventoSolidario> listAllEventsWithMembers ();
    @Query("SELECT DISTINCT ve FROM EventoSolidario e JOIN e.voluntariosExternos ve WHERE e.idEventoSolidario = :id")
    List<VoluntarioExterno> listAllExternalVolunteersByEventId (@Param("id") Long id);
    @Query("SELECT DISTINCT e FROM EventoSolidario e LEFT JOIN FETCH e.miembros m LEFT JOIN FETCH m.rol LEFT JOIN FETCH e.voluntariosExternos v WHERE e.fechaHora >= :fechaActual")
    List<EventoSolidario> listAllFutureEvents (@Param("fechaActual") LocalDateTime fechaActual);
    @Query("SELECT DISTINCT e FROM EventoSolidario e JOIN FETCH e.miembros m JOIN FETCH m.rol LEFT JOIN FETCH e.voluntariosExternos v WHERE m.nombreUsuario = :nombreUsuario")
    List<EventoSolidario> getEventsByNombreUsuario (@Param("nombreUsuario") String nombreUsuario);
    @Query("SELECT DISTINCT e FROM EventoSolidario e LEFT JOIN FETCH e.miembros m LEFT JOIN FETCH m.rol LEFT JOIN FETCH e.voluntariosExternos v WHERE e.idEventoSolidario = :idEventoSolidario")
    Optional<EventoSolidario> getByIdEvento (@Param("idEventoSolidario") Long idEventoSolidario);
}