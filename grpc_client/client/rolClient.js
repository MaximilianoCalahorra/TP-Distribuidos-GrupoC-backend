import { createGrpcClient } from "../utils/grpcClientFactory.js";

const rolClient = createGrpcClient(
  "../proto/services/rol_service.proto", //Ruta al proto.
  "RolService" //Nombre del servicio.
);

export default rolClient;