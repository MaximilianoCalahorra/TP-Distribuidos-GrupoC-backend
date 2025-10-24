# **Cliente SOAP - Consulta de Presidentes y ONGs**

Este proyecto contiene un **cliente SOAP** desarrollado en Java con Spring Boot, que permite consultar los datos de los presidentes de las ONGs adheridas a la red y los datos de las propias ONGs mediante el ID de cada organización.

El servicio SOAP utilizado es **solo cliente**, no se requiere implementar un servidor SOAP. La URL del WSDL es: https://soap-app-latest.onrender.com/?wsdl

---

### 🏗️ **Tecnologías**

- Java 21
- Spring Boot 3.5.6
- Apache CXF 4.0.1 (cliente SOAP)
- JAXB (para mapeo de XML a objetos Java)
- Maven (gestión de dependencias y compilación)
- Docker (para contenerización)

---

### ⚙️ **Funcionalidades**

El cliente SOAP ofrece al **PRESIDENTE** de la ONG:

1. **Consulta de ONGs**
   - Permite enviar una lista de IDs de organizaciones (`org_ids`) para obtener los datos de cada ONG.
   - Datos devueltos por ONG:
     - ID
     - Nombre
     - Dirección
     - Teléfono

2. **Consulta de Presidentes**
   - Permite enviar una lista de IDs de organizaciones (`org_ids`) para obtener los datos de los presidentes de esas ONGs.
   - Datos devueltos por Presidente:
     - ID
     - Nombre
     - Dirección
     - Teléfono
     - ID de la ONG a la que pertenece

---

### 🧱 **Estructura del proyecto**

```bash
soap_service/
├── src/main/java/org/empuje_comunitario/soap_service
│   ├── config/          # Configuración del cliente SOAP
│   ├── dto/         # Estructuras de datos
│   ├── headers/          # Autenticación
│   └── service/            # Lógica
├── src/main/resources
│   └── application.properties
├── pom.xml              # Dependencias y plugins
└── Dockerfile           # Para construir la imagen Docker
```

---

### 🚀 **Ejecución del proyecto**

1. Clonar el repositorio:

    ```bash
    git clone https://github.com/MaximilianoCalahorra/TP-Distribuidos-GrupoC-backend
    cd TP-Distribuidos-GrupoC-backend/soap_service
    ```

2. Correr la clase ```SoapServiceApplication.java``` para levantar el proyecto.

4. El cliente SOAP quedará disponible en: http://localhost:8084