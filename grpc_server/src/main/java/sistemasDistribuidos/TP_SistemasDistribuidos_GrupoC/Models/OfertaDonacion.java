package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
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
@Table(name = "ofertas_donaciones")

public class OfertaDonacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOfertaDonacion;

    @Column(name = "id_oferta_donacion_origen", nullable = true)
    private String idOfertaDonacionOrigen; 
    
    @Column(name = "id_organizacion", nullable = false)
    private String idOrganizacion;
    
    // Relación OneToMany con los items ofrecidos
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_oferta_donacion")
    private List<ItemDonacion> items = new ArrayList<>();
}
