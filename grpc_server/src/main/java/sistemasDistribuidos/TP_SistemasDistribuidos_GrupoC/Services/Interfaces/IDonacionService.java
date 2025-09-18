package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearDonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.DonacionDTO;
import java.util.List;

public interface IDonacionService {
    public DonacionDTO crearDonacion(CrearDonacionDTO crearDonacionDTO);
    public List<DonacionDTO> traerDonacionesPorEvento(Long idEventoSolidario);
}