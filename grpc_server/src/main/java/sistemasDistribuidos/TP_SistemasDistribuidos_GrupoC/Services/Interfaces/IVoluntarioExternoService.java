package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.VoluntarioExternoDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.VoluntarioExterno;

public interface IVoluntarioExternoService {
	///Crear voluntario externo:
	public VoluntarioExternoDTO crearVoluntarioExterno(VoluntarioExternoDTO voluntario);
	
	///Obtener DTO de voluntario externo por id:
    public VoluntarioExternoDTO obtenerDTOPorId(Long idVoluntarioExterno);
    
    ///Obtener entidad de voluntario externo por id:
    public VoluntarioExterno obtenerEntidadPorId(Long idVoluntarioExterno);
}
