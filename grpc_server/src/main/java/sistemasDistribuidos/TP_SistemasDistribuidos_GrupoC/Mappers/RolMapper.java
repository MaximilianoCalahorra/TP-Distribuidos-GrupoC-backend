package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Rol;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.NombreRol;
import proto.dtos.RolProto;
import proto.dtos.Nombre;

public class RolMapper {

	// =======================
    // Entidad <-> Proto
    // =======================
	
	// Rol <-> RolProto
    public static RolProto aProto(Rol entidad) {
        if (entidad == null) return null;

        Nombre nombreProto = Nombre.DESCONOCIDO;
        if (entidad.getNombreRol() != null) {
            try {
                nombreProto = Nombre.valueOf(entidad.getNombreRol().name());
            } catch (IllegalArgumentException e) {
                nombreProto = Nombre.DESCONOCIDO;
            }
        }

        return RolProto.newBuilder()
                .setIdRol(entidad.getIdRol() != null ? entidad.getIdRol() : 0L)
                .setNombre(nombreProto)
                .build();
    }

    // RolProto <-> Rol
    public static Rol aEntidad(RolProto proto) {
        if (proto == null) return null;

        Rol entidadRol = new Rol();
        entidadRol.setIdRol(proto.getIdRol());

        try {
            entidadRol.setNombreRol(NombreRol.valueOf(proto.getNombre().name()));
        } catch (IllegalArgumentException e) {
            entidadRol.setNombreRol(NombreRol.DESCONOCIDO);
        }

        return entidadRol;
    }
}