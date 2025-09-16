package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearEventoSolidarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.ModificarEventoSolidarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.EventoSolidarioDTO;
import java.util.List;

public interface IEventoSolidarioService {

    /// Agregar un evento solidario
    void crearEvento(CrearEventoSolidarioDTO dto);
    /// Modificar un evento solidario
    void modificarEvento(ModificarEventoSolidarioDTO dto);
    /// eliminar fisicamente un evento solidario
    void eliminarEvento(Long idEventoSolidario);
    ///  obtengo todos los eventos solidarios
    List<EventoSolidarioDTO> obtenerTodos();
    ///  obtengo un evento solidario por ID
    EventoSolidarioDTO obtenerPorId(Long idEventoSolidario);

}
