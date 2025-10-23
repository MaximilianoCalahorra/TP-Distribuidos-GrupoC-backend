package org.empuje_comunitario.graphql_service.model;

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

    @Column(name = "cantidad", nullable = false)
    @PositiveOrZero
    private int cantidad;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_evento_solidario", nullable = false)
    private EventoSolidario eventoSolidario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_inventario", nullable = false)
    private Inventario inventario;
}