import express from 'express';
import {
  listarOfertasDonacionesInternas,
  listarOfertasDonacionesExternas,
  crearOfertaDonacionInterna
} from '../controllers/ofertaDonacionController.js';

const router = express.Router();

// Rutas para ofertas de donaciones:
router.get('/internas', listarOfertasDonacionesInternas);
router.get('/externas', listarOfertasDonacionesExternas);
router.post('/', crearOfertaDonacionInterna);

export default router;