package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import proto.dtos.voluntario_externo.VoluntarioExternoProto;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.MiembroDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.VoluntarioExternoDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.VoluntarioExterno;

public class VoluntarioExternoMapper {
	// =======================
    // Entidad <-> DTOs
    // =======================
	
	// VoluntarioExternoDTO <-> VoluntarioExterno
    public static VoluntarioExternoDTO aDTO(VoluntarioExterno entidad) {
        if (entidad == null) return null;

        VoluntarioExternoDTO dto = new VoluntarioExternoDTO();

        if (entidad.getIdVoluntarioExterno() != null && entidad.getIdVoluntarioExterno() > 0) {
            dto.setIdVoluntarioExterno(entidad.getIdVoluntarioExterno());
        }
        
        dto.setNombre(entidad.getNombre());
        dto.setApellido(entidad.getApellido());
        dto.setTelefono(entidad.getTelefono());
        dto.setEmail(entidad.getEmail());
        dto.setIdOrganizacion(entidad.getIdOrganizacion());
        
        return dto;
    }

    public static VoluntarioExterno aEntidad(VoluntarioExternoDTO dto) {
        if (dto == null) return null;

        VoluntarioExterno entidad = new VoluntarioExterno();
        
        entidad.setNombre(dto.getNombre());
        entidad.setApellido(dto.getApellido());
        entidad.setTelefono(dto.getTelefono());
        entidad.setEmail(dto.getEmail());
        entidad.setIdOrganizacion(dto.getIdOrganizacion());

        return entidad;
    }
	
    // =======================
    // DTOs <-> Protos
    // =======================
    
    // VoluntarioExternoDTO <-> VoluntarioExternoProto
    public static VoluntarioExternoDTO aDTO(VoluntarioExternoProto proto) {
        if (proto == null) return null;

        VoluntarioExternoDTO dto = new VoluntarioExternoDTO();
        
        if (proto.getIdVoluntarioExterno() != 0) {
            dto.setIdVoluntarioExterno(proto.getIdVoluntarioExterno());
        }
        
        dto.setNombre(proto.getNombre());
        dto.setApellido(proto.getApellido());
        dto.setTelefono(proto.getTelefono());
        dto.setEmail(proto.getEmail());
        dto.setIdOrganizacion(proto.getIdOrganizacion());

        return dto;
    }

    public static VoluntarioExternoProto aProto(VoluntarioExternoDTO dto) {
        if (dto == null) return null;

        return VoluntarioExternoProto.newBuilder()
        		.setIdVoluntarioExterno(dto.getIdVoluntarioExterno() != null ? dto.getIdVoluntarioExterno() : 0)
                .setNombre(dto.getNombre())
                .setApellido(dto.getApellido())
                .setTelefono(dto.getTelefono())
                .setEmail(dto.getEmail())
                .setIdOrganizacion(dto.getIdOrganizacion())
                .build();
    }
    
    // =====================================
    // VoluntarioExternoProto <-> MiembroDTO
    // =====================================
    
    public static MiembroDTO aMiembroDTO(VoluntarioExternoProto proto) {
    	if (proto == null) return null;
    	
    	MiembroDTO dto = new MiembroDTO();
        
        dto.setNombre(proto.getNombre());
        dto.setApellido(proto.getApellido());
        dto.setTelefono(proto.getTelefono());
        dto.setEmail(proto.getEmail());

        return dto;
    }
}
