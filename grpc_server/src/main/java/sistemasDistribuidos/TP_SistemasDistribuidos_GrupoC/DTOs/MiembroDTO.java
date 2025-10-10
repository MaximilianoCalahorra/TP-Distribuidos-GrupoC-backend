package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Rol;

@Getter
@Setter
@NoArgsConstructor
public class MiembroDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Rol rol;

    // Constructor con todos los campos
    public MiembroDTO(String nombre, String apellido, String email, Rol rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.rol = rol;
    }
}