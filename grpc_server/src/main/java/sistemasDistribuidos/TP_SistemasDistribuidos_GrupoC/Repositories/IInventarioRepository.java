package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Enums.Categoria;
import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.Inventario;

@Repository("inventarioRepository")
public interface IInventarioRepository extends JpaRepository<Inventario, Long> {
    ///Obtener el inventario con determinada categoría y descripción:
    public Optional<Inventario> findByCategoriaAndDescripcion(Categoria categoria, String descripcion);

    ///Obtener los inventarios activos:
    public List<Inventario> findByEliminadoFalse();
}