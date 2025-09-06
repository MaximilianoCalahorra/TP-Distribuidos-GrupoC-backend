package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTimeMapper {

    private static final ZoneId ZONE_ARG = ZoneId.of("America/Argentina/Buenos_Aires");
    
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
}