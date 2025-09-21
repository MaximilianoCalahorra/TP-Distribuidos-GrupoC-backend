import { createGrpcClient } from "../utils/grpcClientFactory.js";

const donacionClient = createGrpcClient(
  "../proto/services/donacion_service.proto", // Ruta al proto
  "DonacionService" // Nombre del servicio
);

export default donacionClient;