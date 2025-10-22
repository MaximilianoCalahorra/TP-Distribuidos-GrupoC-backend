package org.empuje_comunitario.rest_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.empuje_comunitario.rest_service.enums.NombreRol;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table (name = "roles")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    @Column(name = "nombre", nullable = false)
    @Enumerated(EnumType.STRING)
    private NombreRol nombreRol;
}
