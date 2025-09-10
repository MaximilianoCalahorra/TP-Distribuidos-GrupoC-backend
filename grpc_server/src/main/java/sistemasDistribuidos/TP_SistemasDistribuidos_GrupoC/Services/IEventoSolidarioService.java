package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.EventoSolidario;

public interface IEventoSolidarioService {
    EventoSolidario obtenerEventoPorId(Long idEvento);
}
