package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.MiembroDTO;

public interface IEventoExternoService {
	///Eliminar evento:
    public void eliminarEventoExterno(Long idEventoExterno);
    
    ///Adherir participante interno:
    public void adherirParticipanteInterno(Long idEventoExterno, String idOrganizador, MiembroDTO participanteInterno);
}
