package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Rol;

@Getter
@Setter
@NoArgsConstructor
public class LoginUsuarioResponseDTO     {
    private String nombre;
    private String nombreUsuario;
    private String apellido;
    private String email;
    private String clave;
    private Rol rol;

    // Constructor con todos los campos
    public LoginUsuarioResponseDTO(String nombre, String nombreUsuario, String apellido, String email, String clave, Rol rol) {
        this.nombre = nombre;
        this.nombreUsuario = nombreUsuario;
        this.apellido = apellido;
        this.email = email;
        this.clave = clave;
        this.rol = rol;
    }
}
