# REST Service - ONG Empuje Comunitario

Este servicio web está desarrollado en **Spring Boot** con **Maven** y se conecta a una base de datos **MySQL**. Se encarga de manejar el **informe de donaciones exportable a Excel** y la **gestión de filtros personalizados para la consulta de la participación en eventos propios** de la ONG Empuje Comunitario, utilizando **REST**.

---

### 🏗️ **Arquitectura**

- **Spring Boot**: framework principal para la aplicación.

- **REST**: endpoints para generar informes y gestionar filtros.

- **MySQL**: base de datos relacional para persistencia de datos.

- **Maven**: gestión de dependencias y ciclo de vida del proyecto.

- **DTOs, Repositories, Mappers y Controllers**: arquitectura en capas para separar responsabilidades.

---

### 📦 **Entidades principales**

- **TransferenciaDonacion**: representa las donaciones entre ONGs, con categoría, cantidad y fecha de alta.

- **FiltroEvento**: permite que cada usuario guarde sus filtros de búsqueda para informes de participación en eventos propios.

---

### ⚙️ **Funcionalidades**

#### 🎁 **Informe Excel de donaciones**

- Endpoint REST que permite descargar un archivo Excel con las donaciones registradas.

- Agrupación por hojas en la planilla según categoría.

- Cada hoja contiene el detalle de los registros encontrados sin realizar sumatoria, con columnas como:

    - Fecha de Alta

    - Descripción

    - Cantidad

    - Eliminado

    - Usuario Alta

    - Usuario Modificación

#### 🔖 **Filtros personalizados de participación en eventos propios**

- ABM de filtros guardados por usuario.

- Permite seleccionar, editar o eliminar filtros previamente guardados.

---

### 🚀 **Ejecución del proyecto**

1. Clonar el repositorio:

    ```bash
    git clone https://github.com/MaximilianoCalahorra/TP-Distribuidos-GrupoC-backend
    cd TP-Distribuidos-GrupoC-backend/rest_service
    ```

2. Cargar las variables de entorno para la base de datos MySQL en ```application.properties```:

    ```bash
    spring.datasource.url=jdbc:mysql://${DATABASE_HOST}:${DATABASE_PORT}/${DATABASE_NAME}?createDatabaseIfNotExist=true
    spring.datasource.username=${DATABASE_USERNAME}
    spring.datasource.password=${DATABASE_PASSWORD}
    ```

3. Correr la clase ```RestServiceApplication.java``` para levantar el proyecto.

4. El servicio REST quedará disponible en: http://localhost:8083

---

### 👥 **Roles y permisos**

| Rol             | Acciones principales                                                                                                           |
| --------------- | ------------------------------------------------------------------------------------------------------------------------------ |
| **PRESIDENTE**  | Puede generar el informe Excel de donaciones y gestionar los filtros de participación en eventos propios, pudiendo filtrar por cualquier usuario |
| **COORDINADOR** | Puede gestionar sus propios filtros para la participación en eventos propios, pudiendo filtrar por cualquier usuario |
| **VOCAL**       | Solamente puede gestionar filtros de eventos propios, pero solo filtrar por usuario con él mismo |
| **VOLUNTARIO**  | Solo puede gestionar sus propios filtros de participación en eventos propios, pudiendo filtrar exclusivamente por usuario con él mismo |

