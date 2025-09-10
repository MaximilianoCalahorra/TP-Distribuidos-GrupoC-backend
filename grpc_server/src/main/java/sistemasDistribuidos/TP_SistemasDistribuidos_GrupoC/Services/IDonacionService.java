package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearDonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.DonacionDTO;

import java.util.List;

public interface IDonacionService {
    void crearDonacion(CrearDonacionDTO crearDonacionDTO, Long idUsuario);
    List<DonacionDTO> traerDonacionesPorEvento(Long idEvento);
}