package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces;

import java.util.List;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearInventarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.InventarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.ModificarInventarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Inventario;

public interface IInventarioService {
	///Obtener todos los inventarios:
	public List<InventarioDTO> inventarios();
	
	///Agregar un inventario:
	public InventarioDTO crear(CrearInventarioDTO inventario);
	
	///Modificar un inventario:
	public InventarioDTO modificar(ModificarInventarioDTO inventario);
	
	///Eliminar lógicamente un inventario:
	public boolean eliminarLogico(Long idInventario);

    /// obtengo inventario por ID
    Inventario obtenerInventarioPorId(Long idInventario);

    void actualizarInventario(Inventario inventario); // ← Para actualizar directo la entidad
}