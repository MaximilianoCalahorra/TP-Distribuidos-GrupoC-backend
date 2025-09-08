package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginUsuarioDTO {
    // Puede ser el nombre de usuario o el email
    private String nombreUsuario;
    private String clave;

    // Constructor con todos los campos
    public LoginUsuarioDTO(String nombreUsuario, String clave) {
        this.nombreUsuario = nombreUsuario;
        this.clave = clave;
    }
}