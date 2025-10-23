package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "transferencias_donaciones")
public class TransferenciaDonacion {
	///Atributos:
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransferenciaDonacion;
	
	@Column(name = "id_solicitud_donacion", nullable = true)
	private String idSolicitudDonacion;
	
	@Column(name = "id_organizacion_donante", nullable = false)
	private String idOrganizacionDonante;
	
	@Column(name = "id_organizacion_receptora", nullable = false)
	private String idOrganizacionReceptora;
	
	@Column(name = "fecha_hora", nullable = false)
	private LocalDateTime fechaHora;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_alta", nullable = false)
    private Usuario usuarioAlta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_modificacion", nullable = false)
    private Usuario usuarioModificacion;
    
    @Column(name = "eliminado", nullable = false)
    private boolean eliminado;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_transferencia_donacion")
	private List<ItemDonacion> items = new ArrayList<>();
}
