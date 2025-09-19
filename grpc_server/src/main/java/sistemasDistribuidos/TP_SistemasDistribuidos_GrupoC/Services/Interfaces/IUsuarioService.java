package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces;

import jakarta.mail.MessagingException;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearUsuarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.LoginUsuarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.ModificarUsuarioDTO;

public interface IUsuarioService {
    public CrearUsuarioDTO crearUsuario (CrearUsuarioDTO crearUsuarioDTO) throws MessagingException;
    public LoginUsuarioDTO login (LoginUsuarioDTO loginUsuarioDTO);
    public String desactivarUsuario (Long idUsuario);
    public ModificarUsuarioDTO modificarUsuario (ModificarUsuarioDTO modificarUsuarioDTO);
}
