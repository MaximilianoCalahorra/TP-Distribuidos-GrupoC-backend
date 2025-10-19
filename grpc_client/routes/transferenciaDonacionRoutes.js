import express from 'express';
import {
  listarTransferenciasDonaciones,
  listarTransferenciasDonacionesEntrantes,
  listarTransferenciasDonacionesSalientes,
  crearTransferenciaDonacionSaliente
} from '../controllers/transferenciaDonacionController.js';

const router = express.Router();

// Rutas para transferencias de donaciones:
router.get('/', listarTransferenciasDonaciones);
router.get('/entrantes', listarTransferenciasDonacionesEntrantes);
router.get('/salientes', listarTransferenciasDonacionesSalientes);
router.post('/', crearTransferenciaDonacionSaliente);

export default router;