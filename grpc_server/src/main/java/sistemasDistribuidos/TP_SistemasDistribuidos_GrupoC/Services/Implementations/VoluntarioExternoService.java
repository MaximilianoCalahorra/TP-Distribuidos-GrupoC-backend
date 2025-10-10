package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.VoluntarioExternoDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.VoluntarioExternoMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.VoluntarioExterno;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.IVoluntarioExternoRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.IVoluntarioExternoService;

@Service
@RequiredArgsConstructor
public class VoluntarioExternoService  implements IVoluntarioExternoService {
	///Atributo:
    private final IVoluntarioExternoRepository voluntarioExternoRepository;

    ///Crear voluntario externo:
    @Override
    public VoluntarioExterno crearVoluntarioExterno(VoluntarioExternoDTO voluntario) {
    	return voluntarioExternoRepository.save(VoluntarioExternoMapper.aEntidad(voluntario));
    }
    
    ///Obtener voluntario externo por id:
    @Override
    public VoluntarioExternoDTO obtenerDTOPorId(Long idVoluntarioExterno) {
    	Optional<VoluntarioExterno> voluntarioOpt = voluntarioExternoRepository.findById(idVoluntarioExterno);
        if (!voluntarioOpt.isPresent()) {
            throw new IllegalArgumentException("Voluntario externo no encontrado.");
        }
        return VoluntarioExternoMapper.aDTO(voluntarioOpt.get());
    }
    
    ///Obtener entidad de voluntario externo por id:
    @Override
    public Optional<VoluntarioExterno> obtenerEntidadPorEmail(String email) {
    	return voluntarioExternoRepository.findByEmail(email);
    }
}