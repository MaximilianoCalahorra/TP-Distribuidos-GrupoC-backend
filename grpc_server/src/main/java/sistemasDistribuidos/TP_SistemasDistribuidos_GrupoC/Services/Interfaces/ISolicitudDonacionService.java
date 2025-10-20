package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces;

import java.util.List;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.SolicitudDonacionDTO;

public interface ISolicitudDonacionService {
	///Solicitudes de una organizacion:
	public List<SolicitudDonacionDTO> solicitudesDonacionDeOrganizacion(String idOrganizacion);
	
	///Solicitudes de todas las organizaciones menos una:
	public List<SolicitudDonacionDTO> solicitudesDonacionDeDemasOrganizaciones(String idOrganizacion);
	
	///Crear solicitud de donacion interna:
	public void crearSolicitudDonacionInterna(SolicitudDonacionDTO solicitud);
	
	///Crear solicitud de donacion externa:
	public void crearSolicitudDonacionExterna(SolicitudDonacionDTO solicitud);
	
	///Procesa el evento de baja de una solicitud de donación y la elimina:
    public void procesarBajaSolicitud(String idOrganizacion, String idSolicitud);
    
    ///Procesa el evento de baja de una solicitud de donación externa y la elimina:
    public void procesarBajaSolicitudExterna(String idOrganizacion, String idSolicitud);
}
