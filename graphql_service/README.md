# GraphQL Service - ONG Empuje Comunitario

Este servicio web está desarrollado en **Spring Boot** con **Maven** y que se conecta a una base de datos **MySQL**, encargándose de manejar los **informes de donaciones** y la **participación en eventos propios** de la ONG Empuje Comunitario, utilizando **GraphQL**. Además, permite el **ABM (alta, baja, modificación) de filtros personalizados** para cada usuario.

---

### 🏗️ **Arquitectura**

- **Spring Boot**: framework principal para la aplicación.

- **GraphQL**: permite consultas y mutaciones flexibles desde el cliente.

- **MySQL**: base de datos relacional para persistencia de datos.

- **Maven**: gestión de dependencias y ciclo de vida del proyecto.

- **DTOs, Repositories, Mappers y Resolvers**: se utiliza una arquitectura en capas para separar responsabilidades.

---

### 📦 **Entidades principales**
- **TransferenciaDonacion**: con categoría, cantidad y fecha de alta.
- **EventoSolidario**: representa los eventos de la ONG y contiene la participación de miembros.
- **FiltroDonacion**: permite que cada usuario guarde sus filtros de búsqueda para informes de donaciones.

---

### ⚙️ **Funcionalidades**

#### 🎁 **Informe de donaciones**
- Consultar donaciones recibidas o efectuadas.
- Filtros opcionales:
  - Categoría
  - Rango de fechas de alta
  - Estado de eliminado (sí/no/ambos)
- Resultado: listado agrupado por categoría y estado de eliminado, mostrando la suma total de la cantidad donada/recibida por cada categoría.

#### 🔖 **Filtros personalizados de donaciones**
- ABM de filtros guardados por usuario.
- Permite seleccionar, editar o eliminar filtros previamente guardados.

#### 🎉 **Informe de participación en eventos propios**
- Consulta de participación de miembros en eventos no cancelados.
- Filtros opcionales:
  - Rango de fechas
  - Usuario (si es PRESIDENTE o COORDINADOR puede consultar cualquier usuario; caso contrario solo su usuario)
  - Reparto de donaciones (sí/no/ambos)
- Resultado: listado agrupado por mes, mostrando día, nombre del evento, descripción y donaciones.

---

### 🚀 **Ejecución del proyecto**

1. Clonar el repositorio:

    ```bash
    git clone https://github.com/MaximilianoCalahorra/TP-Distribuidos-GrupoC-backend
    cd TP-Distribuidos-GrupoC-backend/graphql_service
    ```

2. Cargar las variables de entorno para la base de datos MySQL en ```application.properties```:

    ```bash
    spring.datasource.url=jdbc:mysql://${DATABASE_HOST}:${DATABASE_PORT}/${DATABASE_NAME}?createDatabaseIfNotExist=true
    spring.datasource.username=${DATABASE_USERNAME}
    spring.datasource.password=${DATABASE_PASSWORD}
    ```

3. Correr la clase ```GraphqlServiceApplication.java``` para levantar el proyecto.

4. El servicio GraphQL quedará disponible en: http://localhost:8082/graphql

---

### 👥 **Roles y permisos**

| Rol                    | Acciones principales                                                        |
| ---------------------- | --------------------------------------------------------------------------- |
| **PRESIDENTE**         | Puede consultar tanto el informe de donaciones como el de eventos propios. También puede gestionar los filtros del informe de donaciones |
| **COORDINADOR**        | Puede consultar el informe de eventos propios |
| **VOCAL** | Puede consultar tanto el informe de donaciones como el de eventos propios. También puede gestionar los filtros del informe de donaciones |
| **VOLUNTARIO** | Puede consultar el informe de eventos propios |
