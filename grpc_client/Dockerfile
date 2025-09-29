# ==============================
# Dockerfile para gRPC Client (Express)
# ==============================
FROM node:20-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos package.json y package-lock.json (si existe)
COPY package*.json ./

# Instalamos dependencias
RUN npm install --production

# Copiamos el resto del código fuente
COPY . .

# Exponemos el puerto que usa el cliente Express
EXPOSE 3000

# Comando para iniciar el servicio
CMD ["npm", "start"]