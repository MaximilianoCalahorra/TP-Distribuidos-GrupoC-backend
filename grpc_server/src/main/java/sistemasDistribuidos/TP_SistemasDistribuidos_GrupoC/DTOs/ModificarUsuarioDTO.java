package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Rol;

@Getter
@Setter
@NoArgsConstructor
public class ModificarUsuarioDTO {
    long idUsuario;
    private String nombreUsuario;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private Rol rol;

    // Constructor con todos los campos
    public ModificarUsuarioDTO(Long idUsuario, String nombreUsuario, String nombre, String apellido,
                           String telefono, String email, Rol rol) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.rol = rol;
    }
}