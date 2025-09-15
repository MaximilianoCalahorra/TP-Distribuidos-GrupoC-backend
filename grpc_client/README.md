# gRPC Client

Cliente en Node.js con **Express** que servirá como intermediario entre el frontend y los servicios gRPC del backend.  
Expone endpoints HTTP para consumir los distintos servicios gRPC y devolver respuestas en JSON.

---

## Requisitos

- Node.js >= 18
- npm >= 9

---

## Instalación

```bash
# Clonar el repositorio
git clone https://github.com/MaximilianoCalahorra/TP-Distribuidos-GrupoC-backend

# Entrar a la carpeta del repositorio
cd TP-Distribuidos-GrupoC-backend

# Entrar al cliente gRPC
cd grpc_client

# Instalar dependencias
npm install
```

## Ejecución

Para iniciar el servidor:

```bash
# Ejecutar normalmente
npm start

# Ejecutar en modo desarrollo con nodemon
npm run dev
```

## Estructura del proyecto

```bash
grpc_client/
├── client/       # Clientes gRPC para cada servicio del backend
├── controllers/  # Controladores HTTP que manejan la entrada/salida de cada petición
├── proto/        # Archivos .proto
│   ├── dtos/     # Definición de mensajes / estructuras de datos
│   └── services/ # Definición de servicios gRPC
├── routes/       # Definición de rutas HTTP y asociación con controllers
└── server.js     # Punto de entrada del servidor Express
```