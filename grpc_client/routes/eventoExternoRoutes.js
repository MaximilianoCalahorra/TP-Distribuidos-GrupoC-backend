import { Router } from 'express';
import { adherirParticipanteInterno } from '../controllers/eventoExternoController.js';

const router = Router();

router.post('/participante-interno', adherirParticipanteInterno); //Adhiere un participante interno a un evento externo.

export default router;