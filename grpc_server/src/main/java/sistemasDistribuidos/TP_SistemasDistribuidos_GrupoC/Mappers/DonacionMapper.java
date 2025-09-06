package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.Categoria;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Donacion;
import proto.dtos.CategoriaProto;
import proto.dtos.CrearDonacionProto;
import proto.dtos.CrearInventarioProto;
import proto.dtos.DonacionProto;

public class DonacionMapper {

	// =======================
    // Entidad <-> DTOs
    // =======================
	
    // DonacionDTO <-> Donacion
    public static DonacionDTO aDTO(Donacion entidad) {
    	//TODO
    }
    
    public static Donacion aEntidad(DonacionDTO dto) {
    	//TODO
    }

    // CrearDonacionDTO <-> Donacion
    public static CrearDonacionDTO aCrearDonacionDTO(Donacion entidad) {
    	//TODO
    }
    
    public static Donacion aEntidad(CrearDonacionDTO dto) {
    	//TODO
    }

    // =======================
    // DTOs <-> Protos
    // =======================
    
    // DonacionDTO <-> DonacionDTO
    public static DonacionDTO aDTO(DonacionProto proto) {
    	if (proto == null) return null;

    	DonacionDTO dto = new DonacionDTO();
    	dto.setDescripcion(proto.getDescripcion());
    	dto.setCantidad(proto.getCantidad());

        try {
        	dto.setCategoria(CategoriaProto.valueOf(proto.getCategoria().name()));
        } catch (IllegalArgumentException e) {
            dto.setCategoria(CategoriaProto.DESCONOCIDA);
        }

        return dto;
    }

    public static DonacionProto aProto(DonacionDTO dto) {
        if (dto == null) return null;
        
        CategoriaProto categoria = CategoriaProto.DESCONOCIDA;
        if (dto.getCategoria() != null) {
            try {
                categoria = Categoria.valueOf(dto.getCategoria().name());
            } catch (IllegalArgumentException e) {
            	categoria = CategoriaProto.DESCONOCIDA;
            }
        }

        return DonacionProto.newBuilder()
                .setCategoria(categoria)
                .setCantidad(dto.getCantidad())
                .build();
    }

    // CrearDonacionDTO <-> CrearDonacionProto
    public static CrearDonacionDTO aCrearDonacionDTO(CrearDonacionProto proto) {
        if (proto == null) return null;
        CrearDonacionDTO dto = new CrearDonacionDTO();
        dto.setIdEvento(proto.getIdEvento());
        dto.setDescripcion(proto.getDescripcion());
        dto.setCantidad(proto.getCantidad());
        
        try {
        	dto.setCategoria(CategoriaProto.valueOf(proto.getCategoria().name()));
        } catch (IllegalArgumentException e) {
            dto.setCategoria(CategoriaProto.DESCONOCIDA);
        }
        
        return dto;
    }

    public static CrearDonacionProto aCrearDonacionProto(CrearDonacionDTO dto) {
    	if (dto == null) return null;
        
        CategoriaProto categoria = CategoriaProto.DESCONOCIDA;
        if (dto.getCategoria() != null) {
            try {
                categoria = Categoria.valueOf(dto.getCategoria().name());
            } catch (IllegalArgumentException e) {
            	categoria = CategoriaProto.DESCONOCIDA;
            }
        }

        return DonacionProto.newBuilder()
        		.setIdEvento(dto.getIdEvento())
                .setCategoria(categoria)
                .setDescripcion(dto.getDescripcion())
                .setCantidad(dto.getCantidad())
                .build();
    }
}