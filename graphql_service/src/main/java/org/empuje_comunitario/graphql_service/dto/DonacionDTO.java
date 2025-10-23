package org.empuje_comunitario.graphql_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.empuje_comunitario.graphql_service.enums.Categoria;

@Getter
@Setter
@NoArgsConstructor
public class DonacionDTO {
    private Categoria categoria;
    private int cantidad;
    private String descripcion;

    // Constructor con todos los campos
    public DonacionDTO(Categoria categoria, int cantidad, String descripcion) {
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
    }
}