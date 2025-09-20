import { createGrpcClient } from "../utils/grpcClientFactory.js";

const eventoSolidarioClient = createGrpcClient(
  "../proto/services/evento_solidario_service.proto", //Ruta al proto.
  "EventoSolidarioService" //Nombre del servicio.
);

export default eventoSolidarioClient;