import express from "express";
import cors from "cors";
import inventarioRoutes from './routes/inventarioRoutes.js';
import rolRoutes from './routes/rolRoutes.js';

const app = express();

//Middleware para parsear JSON en requests:
app.use(express.json());

//Habilitar CORS con el frontend:
app.use(cors({
  origin: "http://localhost:5173",
  methods: ["GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"],
}));

//Inventarios:
app.use('/inventarios', inventarioRoutes);

//Roles:
app.use('/roles', rolRoutes);

//Configuración del puerto:
const PORT = 3000;
app.listen(PORT, () => {
  console.log(`Servidor corriendo en http://localhost:${PORT}`)
});