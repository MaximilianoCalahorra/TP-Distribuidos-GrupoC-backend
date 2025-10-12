package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.OfertaDonacion;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.OfertaDonacionDTO;
import proto.dtos.donaciones.OfertaDonacionProto;
import proto.services.kafka.OfertaDonacionKafkaProto; // crear proto Kafka
import java.util.stream.Collectors;


public class OfertaDonacionMapper {
    
    // =======================
    // Entidad <-> DTOs
    // =======================
    
    // OfertaDonacionDTO <-> OfertaDonacion
    public static OfertaDonacionDTO aDTO(OfertaDonacion entidad) {
        if (entidad == null) return null;

        OfertaDonacionDTO dto = new OfertaDonacionDTO();
        
        dto.setIdOferta(entidad.getIdOferta());
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
        
        entidad.setIdOferta(dto.getIdOferta());
        entidad.setIdOrganizacion(dto.getIdOrganizacion());
        entidad.setItems(dto.getItems().stream()
                        // Mapeo de la lista anidada: ItemDonacionDTO -> ItemDonacion (Entity)
                        .map(ItemDonacionMapper::aEntidad) 
                        .collect(Collectors.toList()));
                        
        return entidad;
    }
    
    // =======================
    // DTOs <-> Protos (gRPC/Común)
    // =======================
    
    // OfertaDonacionDTO <-> OfertaDonacionProto
    public static OfertaDonacionDTO aDTO(OfertaDonacionProto proto) {
        if (proto == null) return null;

        OfertaDonacionDTO dto = new OfertaDonacionDTO();

        dto.setIdOferta(proto.getIdOferta());
        dto.setIdOrganizacion(proto.getIdOrganizacion());
        dto.setItems(proto.getItemsList().stream()
                        .map(ItemDonacionMapper::aDTO) 
                        .collect(Collectors.toList()));
                        
        return dto;
    }

    public static OfertaDonacionProto aProto(OfertaDonacionDTO dto) {
        if (dto == null) return null;

        return OfertaDonacionProto.newBuilder()
                .setIdOferta(dto.getIdOferta())
                .setIdOrganizacion(dto.getIdOrganizacion())
                .addAllItems(dto.getItems().stream()
                        .map(ItemDonacionMapper::aProto)
                        .collect(Collectors.toList()))
                .build();
    }
    
    // =======================
    // DTOs <-> Protos (Kafka Específico)
    // =======================
    
    // OfertaDonacionDTO <-> OfertaDonacionKafkaProto
    public static OfertaDonacionDTO aDTO(OfertaDonacionKafkaProto proto) {
        if (proto == null) return null;

        OfertaDonacionDTO dto = new OfertaDonacionDTO();
        
        dto.setIdOferta(proto.getIdOferta());
        dto.setIdOrganizacion(proto.getIdOrganizacion());
        dto.setItems(proto.getItemsList().stream()
                        .map(ItemDonacionMapper::aDTO) 
                        .collect(Collectors.toList()));
                        
        return dto;
    }

    public static OfertaDonacionKafkaProto aOfertaDonacionKafkaProto(OfertaDonacionDTO dto) {
        if (dto == null) return null;

        // Utilizamos el builder estándar de Protobuf
        return OfertaDonacionKafkaProto.newBuilder()
                .setIdOferta(dto.getIdOferta())
                .setIdOrganizacion(dto.getIdOrganizacion())
                .addAllItems(dto.getItems().stream()
                        .map(ItemDonacionMapper::aProto) 
                        .collect(Collectors.toList()))
                .build();
    }
}
