package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.NombreRol;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Rol;

@Repository("rolRepository")
public interface IRolRepository extends JpaRepository<Rol, Long> {
    Rol findByNombreRol(NombreRol nombreRol);
}
