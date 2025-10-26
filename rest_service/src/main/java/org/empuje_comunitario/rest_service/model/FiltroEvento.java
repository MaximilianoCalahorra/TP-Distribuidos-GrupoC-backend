package org.empuje_comunitario.rest_service.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

import org.empuje_comunitario.rest_service.enums.RepartoDonacion;

@Entity
@Table(
		name = "filtros_eventos",
		uniqueConstraints = {
		        @UniqueConstraint(columnNames = {"nombre_filtro", "id_usuario"})
		}
)
@Data 
public class FiltroEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID del usuario que guardó el filtro
    @Column(nullable = false)
    private String emailUsuario; 

    // Nombre que el usuario le da al filtro
    @Column(nullable = false, length = 255)
    private String nombreFiltro; 

    // --- Criterios del Filtro ---
    
    @Column(columnDefinition = "DATETIME(6)")
    private LocalDateTime fechaHoraDesde;

    @Column(columnDefinition = "DATETIME(6)")
    private LocalDateTime fechaHoraHasta;

    @Column(name = "reparto_donaciones", nullable = true)
    @Enumerated(EnumType.STRING)
    private RepartoDonacion repartoDonaciones;
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

}
