package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.NombreRol;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository <Usuario, Long> {

    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol WHERE u.nombreUsuario = :username OR u.email = :username")
    Optional<Usuario> findByNombreUsuario(@Param("username") String username);
    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol WHERE u.nombreUsuario = :username OR u.email = :email")
    Optional<Usuario> findByNombreUsuarioOrEmail(@Param("username") String username, @Param("email") String email);
    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol WHERE u.idUsuario = :idUsuario AND u.activo = :activo")
    Optional <Usuario> findByIdAndActivo (@Param("idUsuario") Long idUsuario, @Param("activo") boolean estado);
    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol")
    List<Usuario> listAllUsers ();
    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol WHERE u.email IN :emails")
    List<Usuario> findAllByEmailIn(@Param("emails") List<String> emails);
    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol WHERE u.idUsuario = :idUsuario")
    Optional<Usuario> findByIdUsuario (@Param("idUsuario") Long idUsuario);
    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol WHERE u.activo = :estado")
    Optional<List<Usuario>> listAllByEstado (@Param("estado") boolean estado);
    Optional<Usuario> findByRol_NombreRol(NombreRol nombreRol);
}
