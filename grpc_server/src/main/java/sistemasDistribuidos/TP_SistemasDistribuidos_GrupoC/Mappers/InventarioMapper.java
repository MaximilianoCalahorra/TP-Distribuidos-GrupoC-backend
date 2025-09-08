package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import proto.dtos.ModificarInventarioProto;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.Categoria;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Inventario;
import proto.dtos.CategoriaProto;
import proto.dtos.CrearInventarioProto;
import proto.dtos.InventarioProto;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.dtos.CrearInventarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.dtos.InventarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.dtos.ModificarInventarioDTO;

public class InventarioMapper {

	// =======================
    // Entidad <-> DTOs
    // =======================

    // InventarioDTO <-> Inventario
    public static InventarioDTO aDTO(Inventario entidad) {
    	//TODO
    }
    
    public static Inventario aEntidad(InventarioDTO dto) {
    	//TODO
    }

    // CrearInventarioDTO <-> Inventario
    public static CrearInventarioDTO aCrearInventarioDTO(Inventario entidad) {
    	//TODO
    }
    
    public static Inventario aEntidad(CrearInventarioDTO dto) {
    	//TODO
    }

    // ModificarInventarioDTO <-> Inventario
    public static ModificarInventarioDTO aModificarInventarioDTO(Inventario entidad) {
    	//TODO
    }
    
    public static Inventario aEntidad(ModificarInventarioDTO dto) {
    	//TODO
    }

    // =======================
    // DTOs <-> Protos
    // =======================

    // InventarioDTO <-> InventarioDTO
    public static InventarioDTO aDTO(InventarioProto proto) {
    	if (proto == null) return null;

    	InventarioDTO dto = new InventarioDTO();
    	dto.setDescripcion(proto.getDescripcion());
    	dto.setCantidad(proto.getCantidad());
    	dto.setEliminado(proto.getEliminado());

        try {
        	dto.setCategoria(CategoriaProto.valueOf(proto.getCategoria().name()));
        } catch (IllegalArgumentException e) {
            dto.setCategoria(CategoriaProto.DESCONOCIDA);
        }

        return dto;
    }

    public static InventarioProto aProto(InventarioDTO dto) {
        if (dto == null) return null;
        
        CategoriaProto categoria = CategoriaProto.DESCONOCIDA;
        if (dto.getCategoria() != null) {
            try {
                categoria = Categoria.valueOf(dto.getCategoria().name());
            } catch (IllegalArgumentException e) {
            	categoria = CategoriaProto.DESCONOCIDA;
            }
        }

        return InventarioProto.newBuilder()
                .setCategoria(categoria)
                .setCantidad(dto.getCantidad())
                .setEliminado(dto.isEliminado())
                .build();
    }

    // CrearInventarioDTO <-> CrearInventarioProto
    public static CrearInventarioDTO aCrearInventarioDTO(CrearInventarioProto proto) {
        if (proto == null) return null;
        CrearInventarioDTO dto = new CrearInventarioDTO();
        dto.setDescripcion(proto.getDescripcion());
        dto.setCantidad(proto.getCantidad());
        
        try {
        	dto.setCategoria(CategoriaProto.valueOf(proto.getCategoria().name()));
        } catch (IllegalArgumentException e) {
            dto.setCategoria(CategoriaProto.DESCONOCIDA);
        }
        
        return dto;
    }

    public static CrearInventarioProto aCrearInventarioProto(CrearInventarioDTO dto) {
    	if (dto == null) return null;
        
        CategoriaProto categoria = CategoriaProto.DESCONOCIDA;
        if (dto.getCategoria() != null) {
            try {
                categoria = Categoria.valueOf(dto.getCategoria().name());
            } catch (IllegalArgumentException e) {
            	categoria = CategoriaProto.DESCONOCIDA;
            }
        }

        return InventarioProto.newBuilder()
                .setCategoria(categoria)
                .setDescripcion(dto.getDescripcion())
                .setCantidad(dto.getCantidad())
                .build();
    }

    // ModificarInventarioDTO <-> ModificarInventarioProto
    public static ModificarInventarioDTO aModificarInventarioDTO(ModificarInventarioProto proto) {
    	if (proto == null) return null;
        ModificarInventarioDTO dto = new ModificarInventarioDTO();
        dto.setDescripcion(proto.getDescripcion());
        dto.setCantidad(proto.getCantidad());
        return dto;
    }

    public static ModificarInventarioProto aModificarInventarioProto(ModificarInventarioDTO dto) {
    	if (dto == null) return null;
        
        return ModificarInventarioProto.newBuilder()
                .setDescripcion(dto.getDescripcion())
                .setCantidad(dto.getCantidad())
                .build();
    }
}