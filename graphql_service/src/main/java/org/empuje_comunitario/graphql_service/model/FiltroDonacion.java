package org.empuje_comunitario.graphql_service.model;

import java.time.LocalDateTime;

import org.empuje_comunitario.graphql_service.enums.EstadoEliminado;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
		name = "filtros_donaciones",
		uniqueConstraints = {
		        @UniqueConstraint(columnNames = {"nombre_filtro", "id_usuario"})
		}
)
public class FiltroDonacion {
	///Atributos:
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFiltroDonacion;
	
	@Column(name = "nombre_filtro", nullable = false)
	private String nombreFiltro;
	
	@Column(name = "fecha_hora_alta_desde", nullable = true)
	private LocalDateTime fechaHoraAltaDesde;
	
	@Column(name = "fecha_hora_alta_hasta", nullable = true)
	private LocalDateTime fechaHoraAltaHasta;
	
	@Column(name = "eliminado", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoEliminado eliminado;
	
	@ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}
