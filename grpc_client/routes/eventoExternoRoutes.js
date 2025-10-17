import { Router } from 'express';
import { adherirParticipanteInterno, listarEventosExternos } from '../controllers/eventoExternoController.js';

const router = Router();

router.post('/participante-interno', adherirParticipanteInterno); //Adhiere un participante interno a un evento externo.
router.get('/', listarEventosExternos); //Lista todos los eventos externos

export default router;