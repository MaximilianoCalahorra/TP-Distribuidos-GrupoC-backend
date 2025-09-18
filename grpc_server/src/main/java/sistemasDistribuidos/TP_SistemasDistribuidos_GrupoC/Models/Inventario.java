package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.Categoria;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
	name = "inventarios",
	uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"categoria", "descripcion"})
	}
)
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInventario;

    @Column(name = "categoria", nullable = false)
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "cantidad", nullable = false)
    @PositiveOrZero
    private int cantidad;

    @Column(name = "eliminado", nullable = false)
    private boolean eliminado;

    @Column(name = "fecha_hora_alta", nullable = false)
    private LocalDateTime fechaHoraAlta;

    @Column(name = "fecha_hora_modificacion", nullable = false)
    private LocalDateTime fechaHoraModificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_alta", nullable = false)
    private Usuario usuarioAlta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_modificacion", nullable = false)
    private Usuario usuarioModificacion;
}
