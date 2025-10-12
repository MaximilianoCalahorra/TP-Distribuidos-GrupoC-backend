package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import proto.dtos.operacion.OperacionProto;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.Operacion;

public class OperacionMapper {

    public static Operacion aEnum(OperacionProto proto) {
        if (proto == null) return Operacion.OPERACION_DESCONOCIDA;

        try {
            return Operacion.valueOf(proto.name());
        } catch (IllegalArgumentException e) {
            return Operacion.OPERACION_DESCONOCIDA;
        }
    }

    public static OperacionProto aProto(Operacion operacion) {
        if (operacion == null) return OperacionProto.OPERACION_DESCONOCIDA;

        try {
            return OperacionProto.valueOf(operacion.name());
        } catch (IllegalArgumentException e) {
            return OperacionProto.OPERACION_DESCONOCIDA;
        }
    }
}