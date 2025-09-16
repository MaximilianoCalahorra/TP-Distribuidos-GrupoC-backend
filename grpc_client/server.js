import express from "express";
import inventarioRoutes from './routes/inventarioRoutes.js';

const app = express();

//Middleware para parsear JSON en requests:
app.use(express.json());

//Inventarios:
app.use('/inventarios', inventarioRoutes);

//Configuración del puerto:
const PORT = 3000;
app.listen(PORT, () => {
  console.log(`Servidor corriendo en http://localhost:${PORT}`)
});