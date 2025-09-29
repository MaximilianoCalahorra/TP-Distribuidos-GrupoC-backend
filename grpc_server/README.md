# Servidor gRPC - ONG Empuje Comunitario

Este proyecto es un **servidor gRPC** desarrollado en **Spring Boot** con **Maven**, que se conecta a una base de datos **MySQL**.
Forma parte de la primera entrega del trabajo práctico de la materia Desarrollo de Software en Sistemas Distribuidos de la Licenciatura en Sistemas de la Universidad Nacional de Lanús y tiene como objetivo gestionar usuarios, roles, inventarios, donaciones y eventos solidarios dentro de una organización.

---

### 🏗️ **Arquitectura**
- **Spring Boot**: framework principal para la aplicación.

- **gRPC**: comunicación cliente-servidor con alto rendimiento.

- **MySQL**: base de datos relacional para persistencia de datos.

- **Maven**: gestión de dependencias y ciclo de vida del proyecto.

- **DTOs, Repositories, Services y Services gRPC**: se utiliza una arquitectura en capas para separar responsabilidades.

---

### 📦 **Entidades principales**
- **Usuario**: tiene un Rol asociado.

- **Rol**: define permisos en el sistema (PRESIDENTE, COORDINADOR, VOCAL, VOLUNTARIO).

- **Inventario**: creado y modificado por usuarios; representa recursos disponibles.

- **Donación**: vinculada a un usuario, un inventario y un evento solidario.

- **Evento Solidario**: tiene participantes (usuarios), puede registrar donaciones y permite gestión de su información.

---

### ⚙️ **Funcionalidades**

#### 👤 **Usuarios**

- Listar usuarios.

- Crear usuario.

- Logueo por email o nombre de usuario + contraseña.

- Desactivar usuario (**baja lógica** → también se lo da de baja de todos los eventos futuros).

- Modificar usuario.

- 🔐 Solo puede realizarlas el **PRESIDENTE**.

- Validaciones: no se pueden repetir ```nombreUsuario``` ni ```email```.

---

#### 📦 **Inventarios**

- Listar inventarios.

- Listar inventarios activos.

- Obtener inventario por **categoría + descripción** (única combinación).

- Crear inventario:

  - Si ya existe la combinación, acumula la cantidad.

  - Si no existe, crea un nuevo registro.

- Modificar inventario.

- Deshabilitar inventario (**baja lógica**).

- Habilitar inventario (se restaura un inventario dado de baja).

- 🔐 Acceso: **PRESIDENTE** y **VOCAL**.

- Validaciones: combinación ```categoría + descripción``` debe ser única.

---

#### 🎁 **Donaciones**

- Crear donación (valida que el inventario tenga stock suficiente).

- Traer donaciones por evento.

- 🔐 Acceso para crear: **PRESIDENTE** o **COORDINADOR**.

---

#### 🎉 **Eventos Solidarios**

- Crear evento (fecha/hora debe ser posterior a la actual, participantes activos).

- Modificar evento (con mismas validaciones).

- Eliminar evento (**borrado físico**).

- Obtener todos los eventos.

- Registrar donaciones (si el evento es pasado).

- Editar participantes (si el evento es futuro).

- 🔐 Acceso:

  - **PRESIDENTE** y **COORDINADOR**: crear, modificar, eliminar, registrar donaciones, editar participantes.

  - **VOLUNTARIO**: sumarse o darse de baja de eventos futuros.

---

### 🚀 **Ejecución del proyecto**

1. Clonar el repositorio

```bash
git clone https://github.com/MaximilianoCalahorra/TP-Distribuidos-GrupoC-backend
cd TP-Distribuidos-GrupoC-backend/grpc_server
```

2. Cargar las variables de entorno para la base de datos MySQL en ```application.properties```:
```bash
spring.datasource.url=jdbc:mysql://${DATABASE_HOST}:${DATABASE_PORT}/${DATABASE_NAME}?createDatabaseIfNotExist=true
spring.datasource.username=${DATABASE_USERNAME}
spring.datasource.password=${DATABASE_PASSWORD}
```

3. Correr la clase ```TpSistemasDistribuidosGrupoCApplication.java``` para levantar el proyecto.

4. El servidor gRPC quedará disponible en el puerto configurado (por defecto ```9090```).

5. Cargar el siguiente script en la base de datos para tener los roles y un presidente: [cargar_datos_iniciales.sql](https://github.com/MaximilianoCalahorra/TP-Distribuidos-GrupoC-backend/blob/master/cargar_datos_iniciales.sql)

Las credenciales del presidente son:
- nombreUsuario: j.perez
- email: j.perez@empuje-comunitario.com
- clave: IzsDXBLFqwPi

---

### 👥 **Roles y permisos**

| Rol             | Acciones principales                                             |
| --------------- | ---------------------------------------------------------------- |
| **PRESIDENTE**  | Gestión completa de usuarios, inventarios, donaciones y eventos. |
| **COORDINADOR** | Gestión de donaciones y eventos.                                 |
| **VOCAL**       | Gestión de inventarios.                                          |
| **VOLUNTARIO**  | Participación en eventos (alta/baja).                            |

---

### 📌 **Notas**

- Las **bajas lógicas** se utilizan en usuarios e inventarios para mantener la trazabilidad.

- Se aplican validaciones de unicidad en emails, nombre de usuario e inventarios (```categoría + descripción```).

- El servidor expone métodos gRPC definidos en archivos ```.proto```.
