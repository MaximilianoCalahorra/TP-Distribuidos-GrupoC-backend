package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces;

public interface IEventoExternoService {
	///Eliminar evento:
    public void eliminarEventoExterno(Long idEventoExterno);
    
    ///Adherir participante interno:
    public void adherirParticipanteInterno(Long idEventoExterno, String idOrganizador, String emailParticipante);
}
