package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.SolicitudDonacion;

public interface ISolicitudDonacionRepository extends JpaRepository<SolicitudDonacion, Long> {
	///Solicitudes de una organización:
    public List<SolicitudDonacion> findByIdOrganizacion(String idOrganizacion);

    ///Solicitudes de todas las organizaciones menos una:
    public List<SolicitudDonacion> findByIdOrganizacionNot(String idOrganizacion);
    
    ///Solicitud de donación por id de origen y de organización:
    Optional<SolicitudDonacion> findByIdSolicitudDonacionOrigenAndIdOrganizacion(String idSolicitudDonacionOrigen, String idOrganizacion);
}
