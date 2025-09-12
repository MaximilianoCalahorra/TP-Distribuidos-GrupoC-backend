package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Rol;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.IRolRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.IRolService;

@Service("rolService")
@RequiredArgsConstructor
public class RolService implements IRolService {
	///Atributo:
	private final IRolRepository rolRepository;
	
	///Obtener todos los roles:
	@Override
	public List<Rol> roles() {
		return rolRepository.findAll();
	}
}