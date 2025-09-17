package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearEventoSolidarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.ModificarEventoSolidarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoSolidario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.EventoSolidarioDTO;
import java.util.List;

public interface IEventoSolidarioService {

    /// Agregar un evento solidario
    public EventoSolidarioDTO crearEventoSolidario(CrearEventoSolidarioDTO dto);
    /// Modificar un evento solidario
    public EventoSolidarioDTO modificarEventoSolidario(ModificarEventoSolidarioDTO dto);
    /// eliminar fisicamente un evento solidario
    public boolean eliminarEventoSolidario(Long idEventoSolidario);
    ///  obtengo todos los eventos solidarios
    public List<EventoSolidarioDTO> obtenerTodos();
    ///  obtengo un evento solidario por ID
    public EventoSolidarioDTO obtenerPorId(Long idEventoSolidario);
    ///  obtengo una entidad evento solidario por ID
    public EventoSolidario obtenerEntidadPorId(Long idEventoSolidario);

}