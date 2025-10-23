package org.empuje_comunitario.graphql_service.mapper;

import org.empuje_comunitario.graphql_service.dto.MiembroDTO;
import org.empuje_comunitario.graphql_service.dto.UsuarioDTO;
import org.empuje_comunitario.graphql_service.model.Usuario;

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
	
    // MiembroDTO <-> Usuario
    public static MiembroDTO aMiembroDTO(Usuario entidad) {
    	if (entidad == null) return null;
    	
    	MiembroDTO dto = new MiembroDTO();

        dto.setNombreUsuario(entidad.getNombreUsuario());
    	dto.setNombre(entidad.getNombre());
    	dto.setApellido(entidad.getApellido());
      	dto.setEmail(entidad.getEmail());
      	dto.setTelefono(entidad.getTelefono());
     	dto.setRol(entidad.getRol());
      
     	return dto;
    }
    
    public static Usuario aEntidad(MiembroDTO dto) {
    	if (dto == null) return null;
    	
    	Usuario entidad = new Usuario();

        entidad.setNombreUsuario((dto.getNombreUsuario()));
    	entidad.setNombre(dto.getNombre());
    	entidad.setApellido(dto.getApellido());
    	entidad.setEmail(dto.getEmail());
    	dto.setTelefono(entidad.getTelefono());
    	entidad.setRol(dto.getRol());
       
    	return entidad;
    }
}