import grpc from '@grpc/grpc-js';
import protoLoader from '@grpc/proto-loader';
import path from 'path';
import { fileURLToPath } from 'url'; 

const __filename = fileURLToPath(import.meta.url); //Convierte la URL del módulo actual en un path del sistema de archivos.
const __dirname = path.dirname(__filename); //Obtiene solo el directorio donde está este archivo (sin el nombre del archivo).

export const createGrpcClient = (serviceProtoPath, serviceName) => {
  //Construye la ruta absoluta al .proto de indicado partiendo del directorio actual:
  const PROTO_PATH = path.join(__dirname, serviceProtoPath);

  //Cargar el archivo .proto:
  const packageDefinition = protoLoader.loadSync(PROTO_PATH, {
    keepCase: true,          //Mantiene los nombres de los campos tal como en el proto.
    longs: String,           //Convierte valores long a string.
    enums: String,           //Convierte enums a string.
    defaults: true,          //Usa valores por defecto de proto3.
    oneofs: true,             //Maneja correctamente campos "oneof".
    includeDirs: [path.join(__dirname, '../proto')]  //Carpeta con protos (DTOs y services).
  });

  //Convertir la definición en un objeto que gRPC pueda usar:
  const proto = grpc.loadPackageDefinition(packageDefinition).proto.services;

  //Crear una instancia del cliente gRPC:
  const client = new proto[serviceName](
    'localhost:9090',                     //Dirección del servidor gRPC
    grpc.credentials.createInsecure()
  );

  return client; //Retorna el cliente gRPC.
}

export default createGrpcClient;