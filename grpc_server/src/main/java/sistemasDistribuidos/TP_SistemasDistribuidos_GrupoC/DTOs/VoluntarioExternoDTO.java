package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VoluntarioExternoDTO {
	///Atributos:
    private Long idVoluntarioExterno;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String idOrganizacion;
    
    ///Constructor con todos los campos:
    public VoluntarioExternoDTO(Long idVoluntarioExterno, String nombre, String apellido, String telefono, String email, String idOrganizacion) {
        this.idVoluntarioExterno = idVoluntarioExterno;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.idOrganizacion = idOrganizacion;
    }
}
