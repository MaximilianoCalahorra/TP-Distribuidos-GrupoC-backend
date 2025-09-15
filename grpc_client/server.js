import express from "express";

const app = express();

//Middleware para parsear JSON en requests:
app.use(express.json());

//Ruta de prueba:
app.get("/", (req, res) => {
  res.send("Servidor Express funcionando!")
})

//Configuración del puerto:
const PORT = 3000;
app.listen(PORT, () => {
  console.log(`Servidor corriendo en http://localhost:${PORT}`)
});