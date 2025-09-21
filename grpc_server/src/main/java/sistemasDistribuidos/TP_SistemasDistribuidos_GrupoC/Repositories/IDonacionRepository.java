package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Donacion;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IDonacionRepository extends JpaRepository<Donacion, Long> {
	@Query("SELECT d FROM Donacion d JOIN FETCH d.inventario WHERE d.eventoSolidario.idEventoSolidario = :idEventoSolidario")
	List<Donacion> findByEventoSolidarioIdEventoSolidarioConInventario(@Param("idEventoSolidario") Long idEventoSolidario);
}
