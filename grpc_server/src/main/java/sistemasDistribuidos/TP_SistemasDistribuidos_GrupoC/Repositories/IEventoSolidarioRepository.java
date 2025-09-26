package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories;

import org.springframework.data.jpa.repository.Query;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoSolidario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("eventoSolidarioRepository")
public interface IEventoSolidarioRepository extends JpaRepository<EventoSolidario, Long> {
    @Query("SELECT DISTINCT e FROM EventoSolidario e LEFT JOIN FETCH e.miembros m LEFT JOIN FETCH m.rol")
    List<EventoSolidario> listAllEvents ();
}