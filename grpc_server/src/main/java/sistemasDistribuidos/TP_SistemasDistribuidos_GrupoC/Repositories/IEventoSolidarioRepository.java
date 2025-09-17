package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoSolidario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("eventoSolidarioRepository")
public interface EventoSolidarioRepository extends JpaRepository<EventoSolidario, Long> {}
