import express from "express";
import cors from "cors";
import inventarioRoutes from './routes/inventarioRoutes.js';
import rolRoutes from './routes/rolRoutes.js';
import eventoSolidarioRoutes from './routes/eventoSolidarioRoutes.js';
import donacionRoutes from './routes/donacionRoutes.js';

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

//Eventos solidarios:
app.use('/eventos-solidarios', eventoSolidarioRoutes);

//Donaciones:
app.use('/donaciones', donacionRoutes);

//ConfiguraciÃ³n del puerto:
const PORT = 3000;
app.listen(PORT, () => {
  console.log(`Servidor corriendo en http://localhost:${PORT}`)
});