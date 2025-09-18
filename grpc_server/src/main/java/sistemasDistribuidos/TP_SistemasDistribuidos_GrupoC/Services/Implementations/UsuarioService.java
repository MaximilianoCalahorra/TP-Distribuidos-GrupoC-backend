package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearUsuarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.LoginUsuarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.UsuarioMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Usuario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.IRolRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.IUsuarioRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.IUsuarioService;
import org.springframework.security.authentication.AuthenticationManager;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService {

    @Autowired
    IUsuarioRepository usuarioRepository;

    @Autowired
    IRolRepository rolRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PreAuthorize("hasRole('PRESIDENTE')")
    @Override
    public CrearUsuarioDTO crearUsuario(CrearUsuarioDTO crearUsuarioDTO) throws MessagingException {

        //Validamos que el usuario que se desea crear, no tenga un email o un nombre de usuario ya existente
        //en la BD.
        Optional<Usuario> usuario = usuarioRepository.findByNombreUsuarioOrEmail(crearUsuarioDTO.getNombreUsuario(), crearUsuarioDTO.getEmail());

        //Si no existe ningun usuario en la BD con ese nombre de usuario o mail...
        if (usuario.isEmpty()) {

            //Mapeamos CrearUsuarioDTO en un Usuario para persistirlo luego en la BD
            Usuario usuarioAGuardar = UsuarioMapper.aEntidad(crearUsuarioDTO);

            //Generamos una clave aleatoria de 12 caracteres
            String clave = RandomStringUtils.randomAlphabetic(12);

            //Una vez generada, la seteamos y la encriptamos
            usuarioAGuardar.setClave(bCryptPasswordEncoder.encode(clave));

            //Obtenemos el rol y lo seteamos en el usuario a guardar para inicializar la relacion
            usuarioAGuardar.setRol(rolRepository.findByNombreRol(crearUsuarioDTO.getRol().getNombreRol()));

            //Guardamos el usuario en la BD
            usuarioRepository.save(usuarioAGuardar);

            //Enviamos mail de confirmacion
            emailService.sendEmail(crearUsuarioDTO.getNombreUsuario(), crearUsuarioDTO.getEmail(), clave);

            return crearUsuarioDTO;

        } else {
            throw new IllegalArgumentException("Ya existe un usuario con ese nombre de usuario o mail en la BD.");
        }
    }

    @Autowired
    EmailService emailService;

    private final AuthenticationManager authenticationManager;

    @Override
    public LoginUsuarioDTO login(LoginUsuarioDTO loginUsuarioDTO) {
        try {

            //Armamos las credenciales de ingreso. 2 posibles combinaciones:
            //Nombre de usuario + clave || Mail + clave
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginUsuarioDTO.getNombreUsuario(),
                    loginUsuarioDTO.getClave());

            //Se validan las credenciales del usuario y si el mismo esta activo
            authenticationManager.authenticate(authToken);

            //Si los datos de ingreso son validos, devolvemos los datos
            return loginUsuarioDTO;

        } catch (Exception e) {
            throw new BadCredentialsException("Las credenciales ingresadas no son validas: " + e);
        }
    }
}