package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.VoluntarioExterno;

@Repository
public interface IVoluntarioExternoRepository extends JpaRepository<VoluntarioExterno, Long> {}
