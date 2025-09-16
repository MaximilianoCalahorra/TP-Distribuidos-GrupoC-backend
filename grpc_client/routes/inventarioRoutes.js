import { Router } from 'express';
import { listarInventarios, listarInventariosActivos, crearInventario, modificarInventario, eliminarLogicoInventario, habilitarInventario } from '../controllers/inventarioController.js';

const router = Router();

router.get('/', listarInventarios); //Inventarios.
router.get('/activos', listarInventariosActivos); //Inventarios activos.
router.post('/', crearInventario); //Crear un inventario.
router.patch('/:id', modificarInventario); //Modificar un inventario.
router.patch('/:id/deshabilitar', eliminarLogicoInventario); //Eliminar lógicamente un inventario.
router.patch('/:id/habilitar', habilitarInventario); //Habilitar un inventario.

export default router;