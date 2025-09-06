package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.NombreRol;

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
