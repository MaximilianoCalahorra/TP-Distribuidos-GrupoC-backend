package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Mappers;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Rol;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.NombreRol;
import proto.dtos.RolProto;
import proto.dtos.Usuario;
import proto.dtos.UsuarioProto;
import proto.dtos.CrearUsuarioProto;
import proto.dtos.LoginUsuarioProto;
import proto.dtos.MiembroProto;
import proto.dtos.Nombre;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.dtos.LoginUsuarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.dtos.MiembroDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.dtos.UsuarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.dtos.crearUsuarioDTO;


public class UsuarioMapper {

    // =======================
    // Entidad <-> DTOs
    // =======================
	
    // UsuarioDTO <-> Usuario
    public static UsuarioDTO aDTO(Usuario entidad) {
    	//TODO
    }
    
    public static Usuario aEntidad(UsuarioDTO dto) {
    	//TODO
    }

    // CrearUsuarioDTO <-> Usuario
    public static crearUsuarioDTO aCrearUsuarioDTO(Usuario entidad) {
    	//TODO
    }
    
    public static Usuario aEntidad(crearUsuarioDTO dto) {
    	//TODO
    }

    // LoginUsuarioDTO <-> Usuario
    public static LoginUsuarioDTO aLoginUsuarioDTO(Usuario entidad) {
    	//TODO
    }
    
    public static Usuario aEntidad(LoginUsuarioDTO dto) {
    	//TODO
    }
    
    // MiembroDTO <-> Usuario
    public static MiembroDTO aMiembroDTO(Usuario entidad) {
    	//TODO
    }
    
    public static Usuario aEntidad(MiembroDTO dto) {
    	//TODO
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
                .setNombreUsuario(dto.getNombreUsuario())
                .setNombre(dto.getNombre())
                .setApellido(dto.getApellido())
                .setTelefono(dto.getTelefono())
                .setEmail(dto.getEmail())
                .setRol(RolMapper.toProto(dto.getRol()))
                .setActivo(dto.isActivo())
                .build();
    }

    // CrearUsuarioDTO <-> CrearUsuarioProto
    public static crearUsuarioDTO aCrearUsuarioDTO(crearUsuarioProto proto) {
        if (proto == null) return null;
        crearUsuarioDTO dto = new crearUsuarioDTO();
        dto.setNombreUsuario(proto.getNombreUsuario);
        dto.setNombre(proto.getNombre());
        dto.setApellido(proto.getApellido());
        dto.setTelefono(proto.getTelefono());
        dto.setEmail(proto.getEmail());
        dto.setRol(RolMapper.aEntidad(proto.getRol()));
        return dto;
    }

    public static CrearUsuarioProto aCrearUsuarioProto(crearUsuarioDTO dto) {
        if (dto == null) return null;
        return CrearUsuarioProto.newBuilder()
                .setNombreUsuario(dto.getNombreUsuario())
                .setNombre(dto.getNombre())
                .setApellido(dto.getApellido())
                .setTelefono(dto.getTelefono())
                .setEmail(dto.getEmail())
                .setRol(RolMapper.toProto(dto.getRol()))
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
                .setRol(RolMapper.toProto(dto.getRol()))
                .build();
    }
}