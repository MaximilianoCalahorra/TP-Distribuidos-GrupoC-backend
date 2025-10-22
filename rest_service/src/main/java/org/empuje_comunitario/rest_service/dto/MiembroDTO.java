package org.empuje_comunitario.rest_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.empuje_comunitario.rest_service.model.Rol;

@Getter
@Setter
@NoArgsConstructor
public class MiembroDTO {
    private String nombreUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
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