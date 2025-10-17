# 🧩 Kafka Service – TP Sistemas Distribuidos - Grupo C

Este microservicio tiene como función principal **gestionar la comunicación asíncrona entre los distintos servicios del sistema** a través de **Apache Kafka**.

---

### ⚙️ Descripción general

El servicio Kafka actúa como **middleware de mensajería** dentro del ecosistema del proyecto.

Está desarrollado con **Spring Boot 3.5.6** y utiliza la dependencia oficial de **Spring Kafka** para:

- Crear y administrar *topics* dentro del broker Kafka.

- Enviar mensajes (productores).

- Escuchar mensajes (consumidores).

- Gestionar *topics* dinámicos asociados a organizaciones o eventos.

---

### 🧱 Estructura del proyecto

```bash
src/main/java/tpSistemasDistribuidos/kafkaService/
│
├── clients/              # Cliente gRPC para llamar métodos gRPC
├── config/
│   └── KafkaConfig.java  # Configuración principal de Kafka
│
├── consumer/             # Implementación de consumidores
│
├── grpc/                 # Implementación de service gRPC para responder llamdos gRPC.
│
└── producer/             # Implementaciones de productores
```

---

### 📄 Archivo principal: ```KafkaConfig.java```

Esta clase configura todos los componentes necesarios para trabajar con Kafka en Spring Boot:

#### 🔧 Configuración general

- **Conexión al broker**: se obtiene de ```application.properties``` (```spring.kafka.bootstrap-servers```).

- **Serialización/Deserialización**: se define para claves y valores tanto para productores como consumidores.

- **Factories**: se crean *ProducerFactory*, *ConsumerFactory* y *KafkaListenerContainerFactory*.

#### 🧩 Creación automática de topics

Al iniciar el servicio, se crean los siguientes topics fijos:

| Topic                       | Descripción                                   |
| --------------------------- | --------------------------------------------- |
| `solicitud-donaciones`      | Publicación de nuevas solicitudes de donación |
| `oferta-donaciones`         | Comunicación de ofertas disponibles           |
| `baja-solicitud-donaciones` | Notificación de bajas de solicitudes          |
| `eventos-solidarios`        | Registro y difusión de eventos solidarios     |
| `baja-evento-solidario`     | Eliminación de eventos solidarios             |

Si alguno de los topics ya existe no se vuelve a crear, se utiliza el existente.

#### 🧠 Topics dinámicos

Se incluyen *helpers* para crear *topics* específicos según una organización o evento:

- `transferencia-donaciones-<id-organizacion>`

- `adhesion-evento-<id-organizador>`

Estos se pueden crear en tiempo de ejecución mediante `crearTopicDinamico(nombreTopic)`.

---

### ⚙️ Archivo de configuración: `application.properties`

Define parámetros generales del microservicio y su conexión con Kafka.

```bash
spring.application.name=kafkaService
server.port=8081

spring.kafka.bootstrap-servers=${KAFKA_BOOTSTRAP_SERVERS:localhost:29092}
spring.kafka.consumer.group-id=${KAFKA_CONSUMER_GROUP_ID:ONG Empuje Comunitario}
spring.kafka.consumer.auto-offset-reset=${KAFKA_AUTO_OFFSET_RESET:earliest}
```

📌 El valor por defecto de `bootstrap-servers` permite correrlo tanto **localmente** como dentro de **Docker**, apuntando al contenedor `kafka-broker`.

---

### 📝 Comunicación dentro de los topics

Todos los mensajes que se envían y reciben a través de los topics utilizan **JSON** como formato estándar. Esto facilita la interoperabilidad entre servicios y el consumo desde distintos lenguajes o clientes.

🔹 Ejemplo de mensaje en eventos-solidarios:
```json
{
	"idOrganizacion": "1",
	"idEvento": "7",
	"nombre": "Evento ONG Empuje Comunitario",
	"descripcion": "Evento de ONG Empuje Comunitario",
	"fechaHora": "2025-10-25T18:30:00"
}
```

---

### 🐳 Docker

El servicio cuenta con un `Dockerfile` multistage compatible con el `docker-compose.yml` general del sistema.

- Expone el puerto **8081**.

- Se conecta al broker Kafka definido en el compose (`kafka-broker`).

- No requiere volumen propio, ya que no persiste datos locales.