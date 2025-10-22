package org.empuje_comunitario.rest_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.empuje_comunitario.rest_service.enums.Categoria;
import org.empuje_comunitario.rest_service.enums.Operacion;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "items_donaciones")
public class ItemDonacion {
	///Atributos:
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItemDonacion;
	
	@Column(name = "categoria", nullable = false)
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
	
	@Column(name = "descripcion", nullable = false)
	private String descripcion;
	
	@Column(name = "cantidad", nullable = false)
	private int cantidad;
	
	@Column(name = "operacion", nullable = false)
	@Enumerated(EnumType.STRING)
	private Operacion operacion;
}
