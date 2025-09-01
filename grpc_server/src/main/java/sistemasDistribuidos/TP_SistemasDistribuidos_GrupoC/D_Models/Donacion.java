package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.D_Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "donaciones")
public class Donacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDonacion;

    @Column(name = "fecha_hora_modificacion", nullable = false)
    private LocalDateTime fechaHoraModificacion;

    @Column(name = "categoria", nullable = false)
    private String categoria;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "cantidad", nullable = false)
    @PositiveOrZero
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_evento_solidario", nullable = false)
    private EventoSolidario eventoSolidario;
}
