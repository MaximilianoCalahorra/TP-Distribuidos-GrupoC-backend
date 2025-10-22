package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.OfertaDonacionDTO;
import java.util.List;

public interface IOfertaDonacionService {
    
    /// Creo oferta interna
    public void crearOfertaDonacionInterna(OfertaDonacionDTO ofertaDTO);
    
    /// Creo oferta externa
    public void crearOfertaDonacionExterna(OfertaDonacionDTO ofertaDTO);

    /// Listo ofertas de la ONG
    public List<OfertaDonacionDTO> listarOfertas();

    /// Listo ofertas propias
    public List<OfertaDonacionDTO> listarOfertasPropias();

    /// Listo ofertas de las demas organizciones
    public List<OfertaDonacionDTO> listarOfertasExternas();

    /// busco una oferta de una organizacion especifica
    public OfertaDonacionDTO buscarOfertaPorIdOrigenYOrganizacion(String idOferta, String idOrganizacion);
}