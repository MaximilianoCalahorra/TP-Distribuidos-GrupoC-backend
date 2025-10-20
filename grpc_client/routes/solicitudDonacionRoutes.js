import express from 'express';
import {
  listarSolicitudesDonacionesInternas,
  listarSolicitudesDonacionesExternas,
  crearSolicitudDonacionInterna,
  procesarBajaSolicitudDonacionInterna
} from '../controllers/solicitudDonacionController.js';

const router = express.Router();

// Rutas para solicitudes de donaciones:
router.get('/internas', listarSolicitudesDonacionesInternas);
router.get('/externas', listarSolicitudesDonacionesExternas);
router.post('/', crearSolicitudDonacionInterna);
router.delete('/:id', procesarBajaSolicitudDonacionInterna);

export default router;