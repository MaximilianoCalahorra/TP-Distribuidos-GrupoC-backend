import { createGrpcClient } from "../utils/grpcClientFactory.js";

const inventarioClient = createGrpcClient(
  "../proto/services/inventario_service.proto", //Ruta al proto.
  "InventarioService" //Nombre del servicio.
);

export default inventarioClient;