package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.Categoria;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Donacion;
import proto.dtos.CategoriaProto;
import proto.dtos.CrearDonacionProto;
import proto.dtos.CrearInventarioProto;
import proto.dtos.DonacionProto;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearDonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.DonacionDTO;

public class DonacionMapper {

	// =======================
    // Entidad <-> DTOs
    // =======================
	
    // DonacionDTO <-> Donacion
    public static DonacionDTO aDTO(Donacion entidad) {
        DonacionDTO dto = new DonacionDTO();
        dto.setIdDonacion(entidad.getIdDonacion());
        dto.setDescripcion(entidad.getDescripcion());
        dto.setCantidad(entidad.getCantidad());
        dto.setCategoria(entidad.getCategoria());
        dto.setFechaHoraModificacion(entidad.getFechaHoraModificacion());
        return dto;
    }
    
    public static Donacion aEntidad(DonacionDTO dto) {
        Donacion entidad = new Donacion();
        entidad.setIdDonacion(dto.getIdDonacion());
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setCantidad(dto.getCantidad());
        entidad.setCategoria(Categoria.valueOf(dto.getCategoria().name()));
        entidad.setFechaHoraModificacion(dto.getFechaHoraModificacion());
        return entidad;
    }

    // CrearDonacionDTO <-> Donacion
    public static CrearDonacionDTO aCrearDonacionDTO(Donacion entidad) {
        CrearDonacionDTO dto = new CrearDonacionDTO();
        dto.setDescripcion(entidad.getDescripcion());
        dto.setCantidad(entidad.getCantidad());
        dto.setCategoria(CategoriaProto.valueOf(entidad.getCategoria().name()));
        return dto;
    }
    
    public static Donacion aEntidad(CrearDonacionDTO dto) {
        Donacion entidad = new Donacion();
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setCantidad(dto.getCantidad());
        entidad.setCategoria(Categoria.valueOf(dto.getCategoria().name()));
        return entidad;
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