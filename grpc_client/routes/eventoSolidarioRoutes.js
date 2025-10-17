import { Router } from 'express';
import { listarEventosSolidarios, crearEventoSolidario, modificarEventoSolidario, eliminarEventoSolidario,
participarDeEventoSolidario, darseDeBajaDeEventoSolidario, traerEventoSolidarioPorId, 
publicarEventoSolidario} from '../controllers/eventoSolidarioController.js';

const router = Router();

router.get('/', listarEventosSolidarios); //Eventos solidarios.
router.post('/', crearEventoSolidario); //Crear un evento solidario.
router.patch('/:id', modificarEventoSolidario); //Modificar un evento solidario.
router.delete('/:id', eliminarEventoSolidario); //Eliminar un evento solidario.
router.post('/alta/:id', participarDeEventoSolidario); //Participar de un evento solidario.
router.post('/baja/:id', darseDeBajaDeEventoSolidario); //Darse de baja de un evento solidario.
router.get('/:id', traerEventoSolidarioPorId); //Obtener un evento solidario por ID.
router.post('/publicar/:id', publicarEventoSolidario); //Publicar un evento solidario.

export default router;