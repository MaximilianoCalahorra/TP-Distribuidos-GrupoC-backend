# Cliente gRPC - ONG Empuje Comunitario

Este proyecto es un **cliente gRPC** desarrollado en **Node.js con Express**, que funciona como **intermediario entre el frontend y el servidor gRPC**.
Expone rutas HTTP que el frontend puede consumir y se encarga de comunicarse con el servidor gRPC para resolver cada operación.

---

### 🏗️ **Arquitectura**

- **Express**: framework web para exponer rutas HTTP.

- **gRPC**: comunicación con el servidor gRPC.

- **ProtoBuf (.proto)**: archivos compartidos con el servidor, definen el contrato de comunicación.

- **Controllers**: lógica para manejar las peticiones HTTP y llamar a los métodos gRPC.

- **Routes**: mapean las rutas HTTP a los controladores.

- **server.js**: punto de entrada; inicializa Express y monta los enrutadores.

---

### 📂 **Estructura del proyecto**

```bash
grpc_client/
├── client/       # Clientes gRPC para cada servicio del backend
├── controllers/  # Controladores HTTP que manejan la entrada/salida de cada petición
├── proto/        # Archivos .proto (idénticos a los del servidor)
│   ├── dtos/     # Definición de mensajes / estructuras de datos
│   └── services/ # Definición de servicios gRPC
├── routes/       # Definición de rutas HTTP y asociación con controllers
└── server.js     # Punto de entrada del servidor Express
```

---

### 📦 **Requisitos**

- Node.js >= 18

- npm >= 9

---

### ⚙️ **Instalación**

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

---

### 🚀 **Ejecución**

Para iniciar el servidor:
```bash
# Ejecutar normalmente
npm start

# Ejecutar en modo desarrollo con nodemon
npm run dev
```
El servidor Express quedará disponible en ```http://localhost:3000```.

---

### 🌐 **Endpoints disponibles**

El cliente expone rutas HTTP organizadas por entidad, que luego traducen las llamadas al servidor gRPC:

#### 👤 **Usuarios (```/usuarios```)**

- ```GET /usuarios``` → Listar usuarios.

- ```POST /usuarios``` → Crear usuario.

- ```POST /usuarios/login``` → Login por email o nombre de usuario + contraseña.

- ```PUT /usuarios/:id``` → Modificar usuario.

- ```DELETE /usuarios/:id``` → Desactivar usuario (baja lógica).

---

#### 📦 **Inventarios (/inventarios)**

- ```GET /inventarios``` → Listar inventarios.

- ```GET /inventarios/activos``` → Listar inventarios activos.

- ```POST /inventarios``` → Crear inventario.

- ```PATCH /inventarios/:id``` → Modificar inventario.

- ```PATCH /inventarios/:id/habilitar``` → Habilitar inventario.

- ```PATCH /inventarios/:id/deshabilitar``` → Deshabilitar inventario (baja lógica).

---

#### 🎁 **Donaciones (/donaciones)**

- ```POST /donaciones/evento/:id``` → Crear donación de un evento.

- ```GET /donaciones/evento/:id``` → Listar donaciones por evento.

---

#### 🎉 **Eventos Solidarios (/eventos-solidarios)**

- ```GET /eventos-solidarios``` → Obtener todos los eventos.

- ```POST /eventos-solidarios``` → Crear evento.

- ```PATCH /eventos-solidarios/:id``` → Modificar evento.

- ```DELETE /eventos-solidarios/:id``` → Eliminar evento.

---

#### 🛡️ **Roles (/roles)**

- ```GET /roles``` → Listar roles.

---

### 🔗 **Relación con el servidor gRPC**

- Este cliente **carga los mismos** ```.proto``` que el servidor gRPC (contrato de comunicación).

- Se instancian clientes gRPC para cada servicio: **Usuarios, Inventarios, Donaciones, Eventos, Roles**.

- Cada endpoint HTTP traduce la petición en una llamada gRPC y devuelve la respuesta en **JSON** al frontend.