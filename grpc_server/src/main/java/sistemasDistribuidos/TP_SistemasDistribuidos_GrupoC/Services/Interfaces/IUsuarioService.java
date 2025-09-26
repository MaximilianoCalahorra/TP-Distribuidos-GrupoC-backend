package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces;

import jakarta.mail.MessagingException;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.*;

import java.util.List;

public interface IUsuarioService {
    public List<UsuarioDTO> listarUsuarios ();
    public CrearUsuarioDTO crearUsuario (CrearUsuarioDTO crearUsuarioDTO) throws MessagingException;
    public LoginUsuarioResponseDTO login (LoginUsuarioDTO loginUsuarioDTO);
    public String desactivarUsuario (Long idUsuario);
    public ModificarUsuarioDTO modificarUsuario (ModificarUsuarioDTO modificarUsuarioDTO);
    public CrearUsuarioDTO traerUsuario (Long idUsuario);
    public String reactivarUsuario (Long idUsuario);
    public List<MiembroDTO> traerUsuariosActivos ();
}
