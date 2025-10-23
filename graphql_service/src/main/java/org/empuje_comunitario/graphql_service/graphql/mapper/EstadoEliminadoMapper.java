package org.empuje_comunitario.graphql_service.graphql.mapper;

import org.empuje_comunitario.graphql_service.enums.EstadoEliminado;

public class EstadoEliminadoMapper {
	// ===========================
    // Enum <-> String
    // ===========================
    
    public static EstadoEliminado aEnum(String estadoEliminadoStr) {
        if (estadoEliminadoStr == null) return EstadoEliminado.DESCONOCIDO;

        try {
            return EstadoEliminado.valueOf(estadoEliminadoStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return EstadoEliminado.DESCONOCIDO;
        }
    }

    public static String aString(EstadoEliminado estadoEliminado) {
        return estadoEliminado != null ? estadoEliminado.name() : "DESCONOCIDO";
    }
}
