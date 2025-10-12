package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.EventoExternoDTO;

import java.util.List;

public interface IEventoExternoService {
    ///Eliminar evento:
    public void eliminarEventoExterno(String idEventoOrigen, String idOrganizacion);

    ///Adherir participante interno:
    public void adherirParticipanteInterno(String idEvento, String idOrganizador, String emailParticipante);

    ///Crear evento:
    public void crearEventoExterno(EventoExternoDTO eventoExterno);

    ///Obtener todos los eventos externos:
    public List<EventoExternoDTO> obtenerTodos();
}
