package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import proto.dtos.usuario.*;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.*;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Usuario;

public class UsuarioMapper {

    // =======================
    // Entidad <-> DTOs
    // =======================
	
    // UsuarioDTO <-> Usuario
    public static UsuarioDTO aDTO(Usuario entidad) {
    	if (entidad == null) return null;
    	
    	UsuarioDTO dto = new UsuarioDTO();

        dto.setIdUsuario(entidad.getIdUsuario());
    	dto.setNombreUsuario(entidad.getNombreUsuario());
	    dto.setNombre(entidad.getNombre());
	    dto.setApellido(entidad.getApellido());
	    dto.setTelefono(entidad.getTelefono());
	    dto.setEmail(entidad.getEmail());
	    dto.setActivo(entidad.isActivo());
	      
	    return dto;
    }
    
    public static Usuario aEntidad(UsuarioDTO dto) {
    	if (dto == null) return null;
    	
    	Usuario entidad = new Usuario();
        
    	entidad.setNombreUsuario(dto.getNombreUsuario());
    	entidad.setNombre(dto.getNombre());
    	entidad.setApellido(dto.getApellido());
    	entidad.setTelefono(dto.getTelefono());
    	entidad.setEmail(dto.getEmail());
    	entidad.setRol(dto.getRol());
    	entidad.setActivo(dto.isActivo());

    	return entidad;
    }

    // CrearUsuarioDTO <-> Usuario
    public static CrearUsuarioDTO aCrearUsuarioDTO(Usuario entidad) {
    	if (entidad == null) return null;
    	
    	CrearUsuarioDTO dto = new CrearUsuarioDTO();
        
    	dto.setNombreUsuario(entidad.getNombreUsuario());
    	dto.setNombre(entidad.getNombre());
    	dto.setApellido(entidad.getApellido());
    	dto.setTelefono(entidad.getTelefono());
    	dto.setEmail(entidad.getEmail());
    	dto.setRol(entidad.getRol());
      
    	return dto;
    }
    
    public static Usuario aEntidad(CrearUsuarioDTO dto) {
    	if (dto == null) return null;
    	
    	Usuario entidad = new Usuario();
        
    	entidad.setNombreUsuario(dto.getNombreUsuario());
    	entidad.setNombre(dto.getNombre());
    	entidad.setApellido(dto.getApellido());
    	entidad.setTelefono(dto.getTelefono());
    	entidad.setEmail(dto.getEmail());
        entidad.setActivo(true);
      
    	return entidad;
    }

    // LoginUsuarioDTO <-> Usuario
    public static LoginUsuarioDTO aLoginUsuarioDTO(Usuario entidad) {
    	if (entidad == null) return null;
    	
    	LoginUsuarioDTO dto = new LoginUsuarioDTO();
        
    	dto.setNombreUsuario(entidad.getNombreUsuario());
      	dto.setClave(entidad.getClave());
      
      	return dto;
    }
    
    public static Usuario aEntidad(LoginUsuarioDTO dto) {
    	if (dto == null) return null;
    	
    	Usuario entidad = new Usuario();
        
    	entidad.setNombreUsuario(dto.getNombreUsuario());
    	entidad.setClave(dto.getClave());

    	return entidad;
    }
    
    // MiembroDTO <-> Usuario
    public static MiembroDTO aMiembroDTO(Usuario entidad) {
    	if (entidad == null) return null;
    	
    	MiembroDTO dto = new MiembroDTO();
        
    	dto.setNombre(entidad.getNombre());
    	dto.setApellido(entidad.getApellido());
      	dto.setEmail(entidad.getEmail());
     	dto.setRol(entidad.getRol());
      
     	return dto;
    }
    
    public static Usuario aEntidad(MiembroDTO dto) {
    	if (dto == null) return null;
    	
    	Usuario entidad = new Usuario();
        
    	entidad.setNombre(dto.getNombre());
    	entidad.setApellido(dto.getApellido());
    	entidad.setEmail(dto.getEmail());
    	entidad.setRol(dto.getRol());
       
    	return entidad;
    }

    // ModificarUsuarioDTO <-> Usuario
    public static ModificarUsuarioDTO aModificarUsuarioDTO(Usuario entidad) {
        if (entidad == null) return null;

        ModificarUsuarioDTO dto = new ModificarUsuarioDTO();

        dto.setIdUsuario(entidad.getIdUsuario());
        dto.setNombreUsuario(entidad.getNombreUsuario());
        dto.setNombre(entidad.getNombre());
        dto.setApellido(entidad.getApellido());
        dto.setTelefono(entidad.getTelefono());
        dto.setEmail(entidad.getEmail());
        dto.setRol(entidad.getRol());

        return dto;
    }

    public static Usuario aEntidad(ModificarUsuarioDTO dto) {
        if (dto == null) return null;

        Usuario entidad = new Usuario();
        entidad.setIdUsuario(dto.getIdUsuario());
        entidad.setNombreUsuario(dto.getNombreUsuario());
        entidad.setNombre(dto.getNombre());
        entidad.setApellido(dto.getApellido());
        entidad.setTelefono(dto.getTelefono());
        entidad.setEmail(dto.getEmail());

        return entidad;
    }

    // LoginUsuarioResponseDTO <-> Usuario
    public static LoginUsuarioResponseDTO aLoginUsuarioResponseDTO(Usuario entidad) {
        if (entidad == null) return null;

        LoginUsuarioResponseDTO dto = new LoginUsuarioResponseDTO();

        dto.setNombre(entidad.getNombre());
        dto.setNombreUsuario(entidad.getNombreUsuario());
        dto.setApellido(entidad.getApellido());
        dto.setEmail(entidad.getEmail());
        dto.setRol(entidad.getRol());

        return dto;
    }

    public static Usuario aEntidad(LoginUsuarioResponseDTO dto) {
        if (dto == null) return null;

        Usuario entidad = new Usuario();

        entidad.setNombreUsuario(dto.getNombreUsuario());
        entidad.setNombre(dto.getNombre());
        entidad.setApellido(dto.getApellido());
        entidad.setTelefono(dto.getClave());
        entidad.setEmail(dto.getEmail());
        entidad.setRol(dto.getRol());

        return entidad;
    }

    // =======================
    // DTOs <-> Protos
    // =======================
    
    // UsuarioDTO <-> UsuarioProto
    public static UsuarioDTO aDTO(UsuarioProto proto) {
        if (proto == null) return null;
        
        UsuarioDTO dto = new UsuarioDTO();
        
        dto.setNombreUsuario(proto.getNombreUsuario());
        dto.setNombre(proto.getNombre());
        dto.setApellido(proto.getApellido());
        dto.setTelefono(proto.getTelefono());
        dto.setEmail(proto.getEmail());
        dto.setRol(RolMapper.aEntidad(proto.getRol()));
        dto.setActivo(proto.getActivo());
        
        return dto;
    }

    public static UsuarioProto aProto(UsuarioDTO dto) {
        if (dto == null) return null;
        
        return UsuarioProto.newBuilder()
                .setIdUsuario(dto.getIdUsuario())
                .setNombreUsuario(dto.getNombreUsuario())
                .setNombre(dto.getNombre())
                .setApellido(dto.getApellido())
                .setTelefono(dto.getTelefono())
                .setEmail(dto.getEmail())
                .setRol(RolMapper.aProto(dto.getRol()))
                .setActivo(dto.isActivo())
                .build();
    }

    // CrearUsuarioDTO <-> CrearUsuarioProto
    public static CrearUsuarioDTO aCrearUsuarioDTO(CrearUsuarioProto proto) {
        if (proto == null) return null;
        
        CrearUsuarioDTO dto = new CrearUsuarioDTO();
        
        dto.setNombreUsuario(proto.getNombreUsuario());
        dto.setNombre(proto.getNombre());
        dto.setApellido(proto.getApellido());
        dto.setTelefono(proto.getTelefono());
        dto.setEmail(proto.getEmail());
        dto.setRol(RolMapper.aEntidad(proto.getRol()));
        
        return dto;
    }

    public static CrearUsuarioProto aCrearUsuarioProto(CrearUsuarioDTO dto) {
        if (dto == null) return null;
        
        return CrearUsuarioProto.newBuilder()
                .setNombreUsuario(dto.getNombreUsuario())
                .setNombre(dto.getNombre())
                .setApellido(dto.getApellido())
                .setTelefono(dto.getTelefono())
                .setEmail(dto.getEmail())
                .setRol(RolMapper.aProto(dto.getRol()))
                .build();
    }

    // LoginUsuarioDTO <-> LoginUsuarioProto
    public static LoginUsuarioDTO aLoginUsuarioDTO(LoginUsuarioProto proto) {
        if (proto == null) return null;
        
        LoginUsuarioDTO dto = new LoginUsuarioDTO();
        
        dto.setNombreUsuario(proto.getNombreUsuario());
        dto.setClave(proto.getClave());
        
        return dto;
    }

    public static LoginUsuarioProto aLoginUsuarioProto(LoginUsuarioDTO dto) {
        if (dto == null) return null;
        
        return LoginUsuarioProto.newBuilder()
                .setNombreUsuario(dto.getNombreUsuario())
                .setClave(dto.getClave())
                .build();
    }

    // MiembroDTO <-> MiembroProto
    public static MiembroDTO aMiembroDTO(MiembroProto proto) {
        if (proto == null) return null;
        
        MiembroDTO dto = new MiembroDTO();
        
        dto.setNombre(proto.getNombre());
        dto.setApellido(proto.getApellido());
        dto.setEmail(proto.getEmail());
        dto.setRol(RolMapper.aEntidad(proto.getRol()));
        
        return dto;
    }

    public static MiembroProto aMiembroProto(MiembroDTO dto) {
        if (dto == null) return null;
        
        return MiembroProto.newBuilder()
                .setNombre(dto.getNombre())
                .setApellido(dto.getApellido())
                .setEmail(dto.getEmail())
                .setRol(RolMapper.aProto(dto.getRol()))
                .build();
    }

    // ModificarUsuarioDTO <-> ModificarUsuarioProto
    public static ModificarUsuarioDTO aModificarUsuarioDTO(ModificarUsuarioProto proto) {
        if (proto == null) return null;

        ModificarUsuarioDTO dto = new ModificarUsuarioDTO();

        dto.setIdUsuario(proto.getIdUsuario());
        dto.setNombreUsuario(proto.getNombreUsuario());
        dto.setNombre(proto.getNombre());
        dto.setApellido(proto.getApellido());
        dto.setTelefono(proto.getTelefono());
        dto.setEmail(proto.getEmail());
        dto.setRol(RolMapper.aEntidad(proto.getRol()));

        return dto;
    }

    public static ModificarUsuarioProto aModificarUsuarioProto(ModificarUsuarioDTO dto) {
        if (dto == null) return null;

        return ModificarUsuarioProto.newBuilder()
                .setIdUsuario(dto.getIdUsuario())
                .setNombreUsuario(dto.getNombreUsuario())
                .setNombre(dto.getNombre())
                .setApellido(dto.getApellido())
                .setEmail(dto.getEmail())
                .setTelefono(dto.getTelefono())
                .setRol(RolMapper.aProto(dto.getRol()))
                .build();
    }

    // LoginUsuarioResponseDTO <-> LoginUsuarioResponseProto
    public static LoginUsuarioResponseDTO aLoginUsuarioResponseDTO(LoginUsuarioResponseProto proto) {
        if (proto == null) return null;

        LoginUsuarioResponseDTO dto = new LoginUsuarioResponseDTO();

        dto.setNombreUsuario(proto.getNombreUsuario());
        dto.setNombre(proto.getNombre());
        dto.setApellido(proto.getApellido());
        dto.setEmail(proto.getEmail());
        dto.setRol(RolMapper.aEntidad(proto.getRol()));
        dto.setClave(proto.getClave());

        return dto;
    }

    public static LoginUsuarioResponseProto aLoginUsuarioResponseProto(LoginUsuarioResponseDTO dto) {
        if (dto == null) return null;

        return LoginUsuarioResponseProto.newBuilder()
                .setNombreUsuario(dto.getNombreUsuario())
                .setNombre(dto.getNombre())
                .setApellido(dto.getApellido())
                .setEmail(dto.getEmail())
                .setClave(dto.getClave())
                .setRol(RolMapper.aProto(dto.getRol()))
                .build();
    }
}