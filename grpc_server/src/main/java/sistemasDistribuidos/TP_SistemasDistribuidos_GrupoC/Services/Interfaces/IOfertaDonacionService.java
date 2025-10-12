package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.OfertaDonacionDTO;
import java.util.List;

public interface IOfertaDonacionService {
    
    // ID de nuestra organización para filtrado. Se usa "1" como valor constante.
    String ID_NUESTRA_ORGANIZACION = "1"; 

    /// Creo oferta
    OfertaDonacionDTO crearOfertaDonacion(OfertaDonacionDTO ofertaDTO);

    /// Listo ofertas propias
    List<OfertaDonacionDTO> listarOfertasPropias();

    /// Listo ofertas de las demas organizciones
    List<OfertaDonacionDTO> listarOfertasExternas();

    /// busco una oferta de una organizacion especifica
    OfertaDonacionDTO buscarOfertaPorIdYOrganizacion(String idOferta, String idOrganizacion);
}
