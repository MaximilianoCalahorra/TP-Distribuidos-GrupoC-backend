package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.OfertaDonacion;

import java.util.List;
import java.util.Optional;

public interface IOfertaDonacionRepository extends JpaRepository<OfertaDonacion, Long> {
    
    // Listar ofertas por ID de organización (para nuestras ofertas)
    public List<OfertaDonacion> findByIdOrganizacion(String idOrganizacion);
    
    // Listar todas las ofertas excepto las de nuestra organización (para ofertas externas)
    public List<OfertaDonacion> findByIdOrganizacionIsNot(String idOrganizacion);

    // Método de búsqueda por clave compuesta
    public Optional<OfertaDonacion> findByIdOfertaDonacionOrigenAndIdOrganizacion(String idOferta, String idOrganizacion);
}