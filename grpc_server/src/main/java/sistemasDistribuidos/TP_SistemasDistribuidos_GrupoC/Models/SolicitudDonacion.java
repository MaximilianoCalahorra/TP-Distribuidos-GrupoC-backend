package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
	name = "solicitudes_donaciones",
	uniqueConstraints = {
		@UniqueConstraint(columnNames = {"id_solicitud_donacion_origen", "id_organizacion"})
	}
)
public class SolicitudDonacion {
	///Atributos:
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSolicitudDonacion;
	
	@Column(name = "id_solicitud_donacion_origen", nullable = true)
	private String idSolicitudDonacionOrigen;
	
	@Column(name = "id_organizacion", nullable = false)
	private String idOrganizacion;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_solicitud_donacion")
	private List<ItemDonacion> items = new ArrayList<>();
}
