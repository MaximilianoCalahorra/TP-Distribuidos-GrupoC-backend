import grpc from '@grpc/grpc-js';
import protoLoader from '@grpc/proto-loader';
import path from 'path';
import { fileURLToPath } from 'url';

// Necesario para obtener __dirname en ES Modules
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// Ruta al archivo .proto de Inventario
const PROTO_PATH = path.join(__dirname, '../proto/services/inventario_service.proto');

// 1. Cargar el archivo .proto
const packageDefinition = protoLoader.loadSync(PROTO_PATH, {
  keepCase: true,          // Mantiene los nombres de los campos tal como en el proto
  longs: String,           // Convierte valores long a string
  enums: String,           // Convierte enums a string
  defaults: true,          // Usa valores por defecto de proto3
  oneofs: true,             // Maneja correctamente campos "oneof"
  includeDirs: [path.join(__dirname, '../proto')]  // Carpeta con protos (DTOs y services)
});

// 2. Convertir la definición en un objeto que gRPC pueda usar
const inventarioProto = grpc.loadPackageDefinition(packageDefinition).proto.services;

// 3. Crear una instancia del cliente gRPC
const inventarioClient = new inventarioProto.InventarioService(
  'localhost:9090',                     // Dirección del servidor gRPC (Spring Boot)
  grpc.credentials.createInsecure()     // Credenciales inseguras (sin SSL, para desarrollo)
);

// 4. Exportar el cliente para usarlo en controllers
export default inventarioClient;