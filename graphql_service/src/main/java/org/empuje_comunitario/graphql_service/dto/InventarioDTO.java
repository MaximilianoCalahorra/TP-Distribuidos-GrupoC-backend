package org.empuje_comunitario.graphql_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.empuje_comunitario.graphql_service.enums.Categoria;

@Getter
@Setter
@NoArgsConstructor
public class InventarioDTO {
	private Long idInventario;
    private Categoria categoria;
    private String descripcion;
    private int cantidad;
    private boolean eliminado;

    // Constructor con todos los campos
    public InventarioDTO(Long idInventario, Categoria categoria, String descripcion, int cantidad, boolean eliminado) {
    	this.idInventario = idInventario;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.eliminado = eliminado;
    }
}