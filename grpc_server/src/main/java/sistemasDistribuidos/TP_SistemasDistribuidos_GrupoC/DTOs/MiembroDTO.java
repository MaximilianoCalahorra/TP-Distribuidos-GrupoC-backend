package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Rol;

@Getter
@Setter
@NoArgsConstructor
public class MiembroDTO {
    private String nombreUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private Rol rol;

    // Constructor con todos los campos
    public MiembroDTO(String nombreUsuario, String nombre, String apellido, String email, Rol rol) {
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.rol = rol;
    }
}