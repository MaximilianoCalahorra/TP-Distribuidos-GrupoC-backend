package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Rol;
import proto.dtos.RolProto;

public class RolMapper {

	// =======================
    // Entidad <-> Proto
    // =======================
	
	// Rol <-> RolProto
    public static RolProto aProto(Rol entidad) {
        if (entidad == null) return null;
        
        return RolProto.newBuilder()
                .setNombre(NombreRolMapper.aProto(entidad.getNombreRol()))
                .build();
    }

    // RolProto <-> Rol
    public static Rol aEntidad(RolProto proto) {
        if (proto == null) return null;

        Rol entidadRol = new Rol();
        entidadRol.setNombreRol(NombreRolMapper.aEnum(proto.getNombre()));
        
        return entidadRol;
    }
}