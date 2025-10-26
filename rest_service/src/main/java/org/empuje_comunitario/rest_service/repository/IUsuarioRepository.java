package org.empuje_comunitario.rest_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.empuje_comunitario.rest_service.model.Usuario;

import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository <Usuario, Long> {

    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol WHERE u.nombreUsuario = :username")
    public Optional<Usuario> findByNombreUsuario(@Param("username") String username);
}
