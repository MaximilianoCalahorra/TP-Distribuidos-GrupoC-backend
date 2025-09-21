package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces;

import java.util.List;
import java.util.Optional;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.CrearInventarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.InventarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.ModificarInventarioDTO;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.Categoria;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Inventario;

public interface IInventarioService {
	///Obtener todos los inventarios:
	public List<InventarioDTO> inventarios();
	
	///Obtener los inventarios activos:
	public List<InventarioDTO> inventariosActivos();
	
	///Obtener un inventario por categoria y descripcion:
	public Optional<Inventario> inventarioPorCategoriaYDescripcion(Categoria categoria, String descripcion);
	
	///Agregar un inventario:
	public InventarioDTO crear(CrearInventarioDTO inventario);
	
	///Modificar un inventario:
	public InventarioDTO modificar(ModificarInventarioDTO inventario);
	
	///Eliminar lógicamente un inventario:
	public boolean eliminarLogico(Long idInventario);
  
  ///Habilitar un inventario:
	public boolean habilitarInventario(Long idInventario);
  
  /// obtengo inventario por ID
  public Inventario obtenerInventarioPorId(Long idInventario);
  
  /// Actualizo el registro existente en la base de datos
  public void actualizarInventario(Inventario inventario);

  /// obtengo inventario por ID
  public ModificarInventarioDTO traerInventario(Long idInventario);
}