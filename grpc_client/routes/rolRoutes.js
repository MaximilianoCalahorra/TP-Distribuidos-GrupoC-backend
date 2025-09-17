import { Router } from 'express';
import { listarRoles } from '../controllers/rolController.js';

const router = Router();

router.get('/', listarRoles); //Roles.

export default router;