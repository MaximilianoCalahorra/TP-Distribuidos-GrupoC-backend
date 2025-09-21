import { Router } from 'express';
import { crearUsuario, login, desactivarUsuario, modificarUsuario, listarUsuarios, traerUsuario, reactivarUsuario } from '../controllers/usuarioController.js';

const router = Router();

router.post('/crear', crearUsuario); //Crear usuario.
router.post('/login', login); //Login.
router.post('/modificar/:id', modificarUsuario); //Modificar usuario.
router.post('/desactivar/:id', desactivarUsuario); //Desactivar usuario.
router.get('/list', listarUsuarios); //Listar usuarios.
router.get('/traer/:id', traerUsuario); //Traer usuarios.
router.post('/reactivar/:id', reactivarUsuario); //Reactivar usuario.

export default router;