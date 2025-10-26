package org.empuje_comunitario.rest_service.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "filtros_eventos")
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

    // Campo para el criterio 'SI', 'NO' o 'AMBOS'
    @Column(length = 10)
    private String repartoDonaciones; 
    
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

}
