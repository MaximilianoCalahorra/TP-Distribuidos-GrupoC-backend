import express from 'express';
import {
  listarDonacionesPorEvento,
  crearDonacion
} from '../controllers/donacionController.js';

const router = express.Router();

// Rutas para donaciones
router.get('/evento/:id', listarDonacionesPorEvento);
router.post('/evento/:id', crearDonacion);

export default router;