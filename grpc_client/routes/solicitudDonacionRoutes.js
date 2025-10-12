import express from 'express';
import {
  listarSolicitudesDonacionesInternas,
  listarSolicitudesDonacionesExternas,
  crearSolicitudDonacionInterna
} from '../controllers/solicitudDonacionController.js';

const router = express.Router();

// Rutas para solicitudes de donaciones:
router.get('/internas', listarSolicitudesDonacionesInternas);
router.get('/externas', listarSolicitudesDonacionesExternas);
router.post('/', crearSolicitudDonacionInterna);

export default router;