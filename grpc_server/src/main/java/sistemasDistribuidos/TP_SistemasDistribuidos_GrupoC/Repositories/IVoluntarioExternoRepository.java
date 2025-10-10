package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.VoluntarioExterno;

@Repository
public interface IVoluntarioExternoRepository extends JpaRepository<VoluntarioExterno, Long> {
	///Obtener voluntario externo por email:
	Optional<VoluntarioExterno> findByEmail(String email);
}
