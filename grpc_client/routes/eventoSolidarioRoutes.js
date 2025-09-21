import { Router } from 'express';
import { listarEventosSolidarios, crearEventoSolidario, modificarEventoSolidario, eliminarEventoSolidario } from '../controllers/eventoSolidarioController.js';

const router = Router();

router.get('/', listarEventosSolidarios); //Eventos solidarios.
router.post('/', crearEventoSolidario); //Crear un evento solidario.
router.patch('/:id', modificarEventoSolidario); //Modificar un evento solidario.
router.delete('/:id', eliminarEventoSolidario); //Eliminar un evento solidario.

export default router;