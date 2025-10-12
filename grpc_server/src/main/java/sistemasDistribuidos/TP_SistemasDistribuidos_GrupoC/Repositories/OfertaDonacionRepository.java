package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.OfertaDonacion;

import java.util.List;
import java.util.Optional;

public interface OfertaDonacionRepository extends JpaRepository<OfertaDonacion, Long> {
    
    // Listar ofertas por ID de organización (para nuestras ofertas)
    List<OfertaDonacion> findByIdOrganizacion(String idOrganizacion);
    
    // Listar todas las ofertas excepto las de nuestra organización (para ofertas externas)
    List<OfertaDonacion> findByIdOrganizacionIsNot(String idOrganizacion);

    // Método de búsqueda por clave compuesta
    Optional<OfertaDonacion> findByIdOfertaAndIdOrganizacion(String idOferta, String idOrganizacion);
}
