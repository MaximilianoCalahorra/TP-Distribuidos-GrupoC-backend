package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Implementations;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.*;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers.UsuarioMapper;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Usuario;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.IRolRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories.IUsuarioRepository;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces.IUsuarioService;
import org.springframework.security.authentication.AuthenticationManager;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService {

    @Autowired
    IUsuarioRepository usuarioRepository;

    @Autowired
    IRolRepository rolRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    EmailService emailService;

    private final AuthenticationManager authenticationManager;

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

    @Override
    public MiembroDTO login(LoginUsuarioDTO loginUsuarioDTO) {
        try {

            //Armamos las credenciales de ingreso. 2 posibles combinaciones:
            //Nombre de usuario + clave || Mail + clave
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginUsuarioDTO.getNombreUsuario(),
                    loginUsuarioDTO.getClave());

            //Se validan las credenciales del usuario y si el mismo esta activo
            authenticationManager.authenticate(authToken);

            //Si los datos de ingreso son validos, buscamos al usuario en la BD.
            Optional<Usuario> usuarioAutenticado = usuarioRepository.findByNombreUsuario(authToken.getName());

            MiembroDTO miembroDTO = UsuarioMapper.aMiembroDTO(usuarioAutenticado.get());

            return miembroDTO;

        } catch (Exception e) {
            throw new BadCredentialsException("Las credenciales ingresadas no son validas: " + e);
        }
    }

    @PreAuthorize("hasRole('PRESIDENTE')")
    @Override
    public String desactivarUsuario(Long idUsuario) {

        //Obtenemos el usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userData = (UserDetails) auth.getPrincipal();
        Optional<Usuario> usuarioAutenticado = usuarioRepository.findByNombreUsuario(userData.getUsername());

        //Validamos que el id ingresado no sea el mismo que el del usuario autenticado
        if (usuarioAutenticado.get().getIdUsuario() == idUsuario) {
            throw new IllegalArgumentException("No es posible desactivarse a si mismo.");
        }

        //Validamos que el usuario que se desea desactivar esta activo o existe
        Optional<Usuario> usuario = usuarioRepository.findByIdAndEstado(idUsuario, true);

        if (usuario.isPresent()) {
            //Si el usuario existe y esta activo
            usuario.get().setActivo(false);
            usuarioRepository.save(usuario.get());
        } else {
            throw new IllegalArgumentException("El usuario que se desea desactivar ya esta inactivo o no existe.");
        }

        return "El usuario con id: " + idUsuario + " fue desactivado con exito!";
    }

    @PreAuthorize("hasRole('PRESIDENTE')")
    @Override
    public ModificarUsuarioDTO modificarUsuario(ModificarUsuarioDTO modificarUsuarioDTO) {

        //Obtenemos el usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userData = (UserDetails) auth.getPrincipal();
        Optional<Usuario> usuarioAutenticado = usuarioRepository.findByNombreUsuario(userData.getUsername());

        //Validamos que el id del usuario a modificar no sea el mismo que el del usuario autenticado
        if (usuarioAutenticado.get().getIdUsuario() == modificarUsuarioDTO.getIdUsuario()) {
            throw new IllegalArgumentException("No es posible modificarse a si mismo.");
        }

        //Validamos que el usuario que se desea modificar esta activo o existe
        Optional<Usuario> usuario = usuarioRepository.findByIdAndEstado(modificarUsuarioDTO.getIdUsuario(), true);

        if (usuario.isPresent()) {
            //Si el usuario existe y esta activo
            Usuario usuarioModificado = UsuarioMapper.aEntidad(modificarUsuarioDTO);
            //Seteamos el rol
            usuarioModificado.setRol(rolRepository.findByNombreRol(modificarUsuarioDTO.getRol().getNombreRol()));
            //Mantenemos la clave
            usuarioModificado.setClave(usuario.get().getClave());
            //Mantenemos el estado
            usuarioModificado.setActivo(usuario.get().isActivo());

            //Validamos que el nombre de usuario y mail no existan
            if (usuarioRepository.findByNombreUsuarioOrEmail(modificarUsuarioDTO.getNombreUsuario(),
                    modificarUsuarioDTO.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Ya existe un usuario con ese nombre de usuario o mail en la BD.");
            }

            usuarioRepository.save(usuarioModificado);
        } else {
            throw new IllegalArgumentException("El usuario que se desea modificar no existe o esta desactivado.");
        }

        return modificarUsuarioDTO;
    }

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        List<Usuario> listaUsuarios = usuarioRepository.listAllUsers();
        List <UsuarioDTO> listaUsuariosDTO =new ArrayList<UsuarioDTO>();
        //Cargamos la lista
        for (Usuario usuario : listaUsuarios) {
            UsuarioDTO usuarioDTO = UsuarioMapper.aDTO(usuario);;
            usuarioDTO.setRol(rolRepository.findByNombreRol(usuario.getRol().getNombreRol()));
            listaUsuariosDTO.add(usuarioDTO);
        }
        return listaUsuariosDTO;
    }
}