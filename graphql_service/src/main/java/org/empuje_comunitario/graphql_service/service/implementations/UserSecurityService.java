package org.empuje_comunitario.graphql_service.service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.empuje_comunitario.graphql_service.model.Usuario;
import org.empuje_comunitario.graphql_service.repository.IUsuarioRepository;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    @Autowired
    IUsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNombreUsuario(username)
                .orElseThrow(()-> new UsernameNotFoundException(("Usuario no encontrado")));

        return org.springframework.security.core.userdetails.User.builder()
                .username(usuario.getNombreUsuario())
                .password(usuario.getClave())
                .roles(usuario.getRol().getNombreRol().toString())
                .disabled(!usuario.isActivo())
                .build();
    }
}
