package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import proto.dtos.categoria.CategoriaProto;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.Categoria;

public class CategoriaMapper {

	// ===========================
    // Enum <-> Proto
    // ===========================
	
    public static Categoria aEnum(CategoriaProto proto) {
        if (proto == null) return Categoria.DESCONOCIDA;

        try {
            return Categoria.valueOf(proto.name());
        } catch (IllegalArgumentException e) {
            return Categoria.DESCONOCIDA;
        }
    }

    public static CategoriaProto aProto(Categoria categoria) {
        if (categoria == null) return CategoriaProto.DESCONOCIDA;

        try {
            return CategoriaProto.valueOf(categoria.name());
        } catch (IllegalArgumentException e) {
            return CategoriaProto.DESCONOCIDA;
        }
    }
    
    // ===========================
    // Enum <-> String
    // ===========================
    
    public static Categoria aEnum(String categoriaStr) {
        if (categoriaStr == null) return Categoria.DESCONOCIDA;

        try {
            return Categoria.valueOf(categoriaStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Categoria.DESCONOCIDA;
        }
    }

    public static String aString(Categoria categoria) {
        return categoria != null ? categoria.name() : "DESCONOCIDA";
    }
}