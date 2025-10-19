package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import proto.dtos.item_donacion.ItemDonacionProto;
import proto.services.kafka.ItemSolicitudDonacionKafkaProto;
import proto.services.kafka.ItemTransferenciaDonacionKafkaProto;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.ItemDonacionDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.ItemDonacion;

public class ItemDonacionMapper {
	
	// =======================
    // Entidad <-> DTOs
    // =======================
	
	// ItemDonacionDTO <-> ItemDonacion
    public static ItemDonacionDTO aDTO(ItemDonacion entidad) {
        if (entidad == null) return null;

        ItemDonacionDTO dto = new ItemDonacionDTO();
        
        dto.setIdItemDonacion(entidad.getIdItemDonacion());
        dto.setCategoria(entidad.getCategoria());
        dto.setDescripcion(entidad.getDescripcion());
        dto.setCantidad(entidad.getCantidad());
        dto.setOperacion(entidad.getOperacion());
        
        return dto;
    }

    public static ItemDonacion aEntidad(ItemDonacionDTO dto) {
        if (dto == null) return null;

        ItemDonacion entidad = new ItemDonacion();
        
        if (dto.getCantidad() > 0) {
        	entidad.setCantidad(dto.getCantidad());
        }
        
        entidad.setCategoria(dto.getCategoria());
        entidad.setDescripcion(dto.getDescripcion());
        entidad.setOperacion(dto.getOperacion());

        return entidad;
    }
    
    // =======================
    // DTOs <-> Protos
    // =======================
	
	// ItemDonacionDTO <-> ItemDonacionProto
    public static ItemDonacionDTO aDTO(ItemDonacionProto proto) {
        if (proto == null) return null;

        ItemDonacionDTO dto = new ItemDonacionDTO();
        
        dto.setIdItemDonacion(proto.getIdItemDonacion());
        dto.setCategoria(CategoriaMapper.aEnum(proto.getCategoria()));
        dto.setDescripcion(proto.getDescripcion());
        dto.setCantidad(proto.getCantidad());
        dto.setOperacion(OperacionMapper.aEnum(proto.getOperacion()));
        
        return dto;
    }

    public static ItemDonacionProto aProto(ItemDonacionDTO dto) {
        if (dto == null) return null;

        return ItemDonacionProto.newBuilder()
                .setIdItemDonacion(dto.getIdItemDonacion())
                .setCategoria(CategoriaMapper.aProto(dto.getCategoria()))
                .setDescripcion(dto.getDescripcion())
                .setCantidad(dto.getCantidad())
                .setOperacion(OperacionMapper.aProto(dto.getOperacion()))
                .build();
    }
    
    // ItemDonacionDTO <-> ItemSolicitudDonacionKafkaProto
    public static ItemDonacionDTO aDTO(ItemSolicitudDonacionKafkaProto proto) {
        if (proto == null) return null;

        ItemDonacionDTO dto = new ItemDonacionDTO();
        
        dto.setCategoria(CategoriaMapper.aEnum(proto.getCategoria()));
        dto.setDescripcion(proto.getDescripcion());
        
        return dto;
    }

    public static ItemSolicitudDonacionKafkaProto aItemSolicitudProto(ItemDonacionDTO dto) {
        if (dto == null) return null;

        return ItemSolicitudDonacionKafkaProto.newBuilder()
                .setCategoria(CategoriaMapper.aString(dto.getCategoria()))
                .setDescripcion(dto.getDescripcion())
                .build();
    }
    
    // ItemDonacionDTO <-> ItemTransferenciaDonacionKafkaProto
    public static ItemDonacionDTO aDTO(ItemTransferenciaDonacionKafkaProto proto) {
    	if (proto == null) return null;
    	
    	ItemDonacionDTO dto = new ItemDonacionDTO();
    	
    	dto.setCategoria(CategoriaMapper.aEnum(proto.getCategoria()));
    	dto.setDescripcion(proto.getDescripcion());
    	dto.setCantidad(proto.getCantidad());
    	
    	return dto;
    }
    
    public static ItemTransferenciaDonacionKafkaProto aItemTransferenciaProto(ItemDonacionDTO dto) {
    	if (dto == null) return null;
    	
    	return ItemTransferenciaDonacionKafkaProto.newBuilder()
    			.setCategoria(CategoriaMapper.aString(dto.getCategoria()))
    			.setDescripcion(dto.getDescripcion())
    			.setCantidad(dto.getCantidad())
    			.build();
    }
}
