package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "eventos_solidarios")
public class EventoSolidario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEventoSolidario;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @ManyToMany
    @JoinTable(name = "eventos_solidarios_x_usuarios",
            joinColumns = {@JoinColumn(name = "id_evento_solidario")},
            inverseJoinColumns = {@JoinColumn(name = "id_usuario")}
    )
    private List<Usuario> miembros;
}
