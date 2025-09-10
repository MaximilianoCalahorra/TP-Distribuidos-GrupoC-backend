package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Donacion;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface DonacionRepository extends JpaRepository<Donacion, Long> {
    List<Donacion> findByEventoSolidarioIdEventoSolidario(Long idEvento);
}
