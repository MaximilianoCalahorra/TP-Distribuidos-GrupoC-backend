package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.UsuarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository <Usuario, Long> {

    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol WHERE u.nombreUsuario = :username OR u.email = :username")
    Optional<Usuario> findByNombreUsuario(@Param("username") String username);
    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol WHERE u.nombreUsuario = :username OR u.email = :email")
    Optional<Usuario> findByNombreUsuarioOrEmail(@Param("username") String username, @Param("email") String email);
    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol WHERE u.idUsuario = :idUsuario AND u.activo = :estado")
    Optional <Usuario> findByIdAndEstado (@Param("idUsuario") Long idUsuario, @Param("estado") boolean estado);
    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol")
    List<Usuario> listAllUsers ();
    @Query("SELECT u FROM Usuario u JOIN FETCH u.rol WHERE u.email IN :emails")
    List<Usuario> findAllByEmailIn(@Param("emails") List<String> emails);
}
