package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "eventos_externos")
public class EventoExterno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEventoExterno;

    @Column(name="nombre", nullable = false)
    private String nombre;

    @Column(name="descripcion", nullable = false)
    private String descripcion;

    @Column(name="fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name="id_organizacion", nullable = false)
    private String idOrganizacion;

    @ManyToMany
    @JoinTable(
        name = "eventos_externos_x_usuarios",
        joinColumns = @JoinColumn(name = "id_evento_externo"),
        inverseJoinColumns = @JoinColumn(name = "id_usuario"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_evento_externo", "id_usuario"})
    )
    private List<Usuario> participantesInternos;
}