package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.OfertaDonacion;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.OfertaDonacionDTO;
import proto.dtos.oferta_donacion.OfertaDonacionProto;
import proto.services.kafka.PublicacionOfertaDonacionKafkaProto;

import java.util.stream.Collectors;

public class OfertaDonacionMapper {
    
    // =======================
    // Entidad <-> DTOs
    // =======================
    
    // OfertaDonacionDTO <-> OfertaDonacion
    public static OfertaDonacionDTO aDTO(OfertaDonacion entidad) {
        if (entidad == null) return null;

        OfertaDonacionDTO dto = new OfertaDonacionDTO();
        
        dto.setIdOfertaDonacion(entidad.getIdOfertaDonacion());
        dto.setIdOfertaDonacionOrigen(entidad.getIdOfertaDonacionOrigen());
        dto.setIdOrganizacion(entidad.getIdOrganizacion());
        dto.setItems(entidad.getItems().stream()
                        // Mapeo de la lista anidada: ItemDonacion (Entity) -> ItemDonacionDTO
                        .map(ItemDonacionMapper::aDTO) 
                        .collect(Collectors.toList()));
                        
        return dto;
    }

    public static OfertaDonacion aEntidad(OfertaDonacionDTO dto) {
        if (dto == null) return null;

        OfertaDonacion entidad = new OfertaDonacion();
        
        if (dto.getIdOfertaDonacionOrigen() != null && !dto.getIdOfertaDonacionOrigen().isEmpty()) {
        	entidad.setIdOfertaDonacionOrigen(dto.getIdOfertaDonacionOrigen());
        }
        
        entidad.setIdOrganizacion(dto.getIdOrganizacion());
        entidad.setItems(dto.getItems().stream()
                        // Mapeo de la lista anidada: ItemDonacionDTO -> ItemDonacion (Entity)
                        .map(ItemDonacionMapper::aEntidad) 
                        .collect(Collectors.toList()));
                        
        return entidad;
    }
    
    // ============================
    // DTOs <-> Protos (gRPC/Común)
    // ============================
    
    // OfertaDonacionDTO <-> OfertaDonacionProto
    public static OfertaDonacionDTO aDTO(OfertaDonacionProto proto) {
        if (proto == null) return null;

        OfertaDonacionDTO dto = new OfertaDonacionDTO();

        dto.setIdOfertaDonacion(proto.getIdOfertaDonacion());
        dto.setIdOfertaDonacionOrigen(proto.getIdOfertaDonacionOrigen());
        dto.setIdOrganizacion(proto.getIdOrganizacion());
        dto.setItems(proto.getItemsList().stream()
                        .map(ItemDonacionMapper::aDTO) 
                        .collect(Collectors.toList()));
                        
        return dto;
    }

    public static OfertaDonacionProto aProto(OfertaDonacionDTO dto) {
        if (dto == null) return null;

        return OfertaDonacionProto.newBuilder()
                .setIdOfertaDonacion(dto.getIdOfertaDonacion())
                .setIdOfertaDonacionOrigen(dto.getIdOfertaDonacionOrigen())
                .setIdOrganizacion(dto.getIdOrganizacion())
                .addAllItems(dto.getItems().stream()
                        .map(ItemDonacionMapper::aProto)
                        .collect(Collectors.toList()))
                .build();
    }
    
    // ==================================
    // DTOs <-> Protos (Kafka Específico)
    // ==================================
    
    // OfertaDonacionDTO <-> PublicacionOfertaDonacionKafkaProto
    public static OfertaDonacionDTO aDTO(PublicacionOfertaDonacionKafkaProto proto) {
        if (proto == null) return null;

        OfertaDonacionDTO dto = new OfertaDonacionDTO();
        
        dto.setIdOfertaDonacionOrigen(proto.getIdOferta());
        dto.setIdOrganizacion(proto.getIdOrganizacion());
        dto.setItems(proto.getDonacionesList().stream()
                        .map(ItemDonacionMapper::aDTO) 
                        .collect(Collectors.toList()));
                        
        return dto;
    }
}