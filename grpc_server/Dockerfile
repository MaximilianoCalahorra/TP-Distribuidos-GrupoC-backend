# ==============================
# Stage 1: Build con Maven
# ==============================
FROM maven:3.9.4-eclipse-temurin-21 AS build

WORKDIR /app

# Copiamos pom.xml y descargamos dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos todo el código fuente
COPY src ./src

# Build del jar ejecutable de Spring Boot
RUN mvn clean package -DskipTests

# ==============================
# Stage 2: Imagen final ligera
# ==============================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiamos el jar construido desde el stage 1
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto gRPC
EXPOSE 9090

# Comando por defecto para ejecutar el jar
ENTRYPOINT ["java", "-jar", "app.jar"]