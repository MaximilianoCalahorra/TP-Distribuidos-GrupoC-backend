package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearEventoSolidarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.ModificarEventoSolidarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.EventoSolidarioDTO;
import java.util.List;

public interface IEventoSolidarioService {

    /// Agregar un evento solidario
    public void crearEvento(CrearEventoSolidarioDTO dto);
    /// Modificar un evento solidario
    public void modificarEvento(ModificarEventoSolidarioDTO dto);
    /// eliminar fisicamente un evento solidario
    public void eliminarEvento(Long idEventoSolidario);
    ///  obtengo todos los eventos solidarios
    public List<EventoSolidarioDTO> obtenerTodos();
    ///  obtengo un evento solidario por ID
    public EventoSolidarioDTO obtenerPorId(Long idEventoSolidario);

}