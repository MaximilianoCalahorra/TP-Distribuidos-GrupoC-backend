package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.OfertaDonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.OfertaDonacionMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.IOfertaDonacionRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.IOfertaDonacionService;
// importamos la entidad para guardar
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.OfertaDonacion;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OfertaDonacionService implements IOfertaDonacionService {

    // Constante para identificar a nuestra propia organización
    private static final String ID_NUESTRA_ORGANIZACION = "1";

    @Value("${ong.id}")
    private String ongEmpujeComunitarioId; ///cambiar esto por lo de arriba
    
    private final IOfertaDonacionRepository ofertaDonacionRepository;

    /// Creo oferta
    @Override
    public OfertaDonacionDTO crearOfertaDonacion(OfertaDonacionDTO ofertaDTO) {
        
        // Asigno IDs necesarios (ID Oferta y nuestra ID de organización)
        // La ofertaDTO ya debe venir con los ItemDonacionDTO.
        ofertaDTO.setIdOferta(UUID.randomUUID().toString()); // Generar ID único de la oferta
        ofertaDTO.setIdOrganizacion(ID_NUESTRA_ORGANIZACION); // ID de nuestra ONG ("1")
        
        // Convierto DTO a Entidad
        OfertaDonacion nuevaOferta = OfertaDonacionMapper.aEntidad(ofertaDTO);
        
        // Base de datos local
        OfertaDonacion ofertaGuardada = ofertaDonacionRepository.save(nuevaOferta);
        
        // Retorno DTO de la entidad guardada
        return OfertaDonacionMapper.aDTO(ofertaGuardada);

    }

    /// Listo ofertas propias
    @Override
    public List<OfertaDonacionDTO> listarOfertasPropias() {
        // Usamos findByIdOrganizacion para buscar solo por nuestra ID
        return ofertaDonacionRepository.findByIdOrganizacion(ID_NUESTRA_ORGANIZACION).stream()
                .map(oferta -> OfertaDonacionMapper.aDTO(oferta))
                .collect(Collectors.toList());
    }

    /// Listo ofertas de las demas organizciones
    @Override
    public List<OfertaDonacionDTO> listarOfertasExternas() {
        // Usamos findByIdOrganizacionIsNot para buscar ofertas cuya ID de organización NO sea la nuestra
        return ofertaDonacionRepository.findByIdOrganizacionIsNot(ID_NUESTRA_ORGANIZACION).stream()
                .map(oferta -> OfertaDonacionMapper.aDTO(oferta)) 
                .collect(Collectors.toList());
    } 

    /// busco una oferta de una organizacion especifica
    @Override
    public OfertaDonacionDTO buscarOfertaPorIdYOrganizacion(String idOferta, String idOrganizacion) {
        return ofertaDonacionRepository.findByIdOfertaAndIdOrganizacion(idOferta, idOrganizacion)
                .map(oferta -> OfertaDonacionMapper.aDTO(oferta)) 
                .orElse(null);  // Retorna null si no se encuentra
    }
}
