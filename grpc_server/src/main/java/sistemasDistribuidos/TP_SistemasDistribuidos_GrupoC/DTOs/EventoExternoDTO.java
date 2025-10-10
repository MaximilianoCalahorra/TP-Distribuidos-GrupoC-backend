package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EventoExternoDTO {
	///Atributos:
    private Long idEventoExterno;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaHora;
    private String idOrganizacion;
    private List<MiembroDTO> participantesInternos;

    ///Constructor con todos los campos:
    public EventoExternoDTO(Long idEventoExterno, String nombre, String descripcion, LocalDateTime fechaHora, String idOrganizacion, List<MiembroDTO> participantesInternos) {
        this.idEventoExterno = idEventoExterno;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
        this.idOrganizacion = idOrganizacion;
        this.participantesInternos = participantesInternos;
    }
}