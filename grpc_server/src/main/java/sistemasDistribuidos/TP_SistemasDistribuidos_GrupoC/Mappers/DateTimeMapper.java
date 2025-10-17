package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateTimeMapper {
    ///Atributos:
    private static final ZoneId ZONE_ARG = ZoneId.of("America/Argentina/Buenos_Aires");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    // ===========================
    // LocalDateTime <-> Timestamp
    // ===========================

    public static LocalDateTime desdeProto(Timestamp timestamp) {
        if (timestamp == null) return null;
        return Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos())
                .atZone(ZONE_ARG)
                .toLocalDateTime();
    }

    public static Timestamp aProto(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        Instant instant = localDateTime.atZone(ZONE_ARG).toInstant();
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

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