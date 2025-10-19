import { createGrpcClient } from "../utils/grpcClientFactory.js";

const transferenciaDonacionClient = createGrpcClient(
  "../proto/services/transferencia_donacion_service.proto", //Ruta al proto.
  "TransferenciaDonacionService" //Nombre del servicio.
);

export default transferenciaDonacionClient;