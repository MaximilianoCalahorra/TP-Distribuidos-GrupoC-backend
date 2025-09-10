package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Usuario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.UsuarioRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.IUsuarioService;

@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public Usuario obtenerUsuarioPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + idUsuario));
    }
}
