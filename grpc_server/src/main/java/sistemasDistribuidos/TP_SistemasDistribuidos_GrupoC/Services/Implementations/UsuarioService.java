package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Usuario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.IUsuarioRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.IUsuarioService;

@Service("usuarioService")
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService {

    ///Atributo:
    private final IUsuarioRepository usuarioRepository;

    public Usuario getUsuarioLogueado() {
        return usuarioRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Usuario de prueba no encontrado"));
    }
}