package org.empuje_comunitario.graphql_service.graphql.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeMapper {
	///Atributo:
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
	
	// ===========================
    // LocalDateTime <-> String
    // ===========================

    public static LocalDateTime desdeString(String fechaHora) {
        if (fechaHora == null || fechaHora.isBlank()) return null;
        return LocalDateTime.parse(fechaHora, FORMATTER);
    }

    public static String aString(LocalDateTime fechaHora) {
        if (fechaHora == null) return null;
        return fechaHora.format(FORMATTER);
    }
}
