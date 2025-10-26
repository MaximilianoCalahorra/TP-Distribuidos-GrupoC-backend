package org.empuje_comunitario.rest_service.service.Interfaces;

import org.empuje_comunitario.rest_service.dto.FiltroEventoDTO;
import org.empuje_comunitario.rest_service.model.FiltroEvento;
import org.empuje_comunitario.rest_service.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IFiltroEventoService {

    /// Guardar filtro:
    public FiltroEventoDTO guardarFiltro(FiltroEventoDTO dto);

    /// Modificar filtro:
    public FiltroEventoDTO modificarFiltro(FiltroEventoDTO filtroDonacion);

    /// Eliminar filtro:
    public boolean eliminarFiltro(Long idFiltroDonacion);

    /// Filtro por nombre y usuario:
    public Optional<FiltroEvento> filtroPorNombreYUsuario(String nombreFiltroDonacion, Usuario usuario);

    /// Listar todos los filtros guardados por un usuario:
    public List<FiltroEventoDTO> filtrosUsuarioLogueado();

 
    
    
    
}

