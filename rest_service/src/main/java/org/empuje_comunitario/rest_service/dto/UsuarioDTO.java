package org.empuje_comunitario.rest_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.empuje_comunitario.rest_service.model.Rol;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTO {
    private Long idUsuario;
    private String nombreUsuario;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private Rol rol;
    private boolean activo;

    // Constructor con todos los campos
    public UsuarioDTO(Long idUsuario, String nombreUsuario, String nombre, String apellido,
                      String telefono, String email, Rol rol, boolean activo) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.rol = rol;
        this.activo = activo;
    }
}