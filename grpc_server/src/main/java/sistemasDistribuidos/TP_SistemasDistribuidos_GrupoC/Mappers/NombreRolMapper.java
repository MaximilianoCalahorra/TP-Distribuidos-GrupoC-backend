package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import proto.dtos.rol.Nombre;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.NombreRol;

public class NombreRolMapper {

    public static NombreRol aEnum(Nombre proto) {
        if (proto == null) return NombreRol.DESCONOCIDO;

        try {
            return NombreRol.valueOf(proto.name());
        } catch (IllegalArgumentException e) {
            return NombreRol.DESCONOCIDO;
        }
    }

    public static Nombre aProto(NombreRol rol) {
        if (rol == null) return Nombre.DESCONOCIDO;

        try {
            return Nombre.valueOf(rol.name());
        } catch (IllegalArgumentException e) {
            return Nombre.DESCONOCIDO;
        }
    }
}
