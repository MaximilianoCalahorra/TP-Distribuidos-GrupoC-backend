import { createGrpcClient } from "../utils/grpcClientFactory.js";

const eventoExternoClient = createGrpcClient(
  "../proto/services/evento_externo_service.proto", //Ruta al proto.
  "EventoExternoService" //Nombre del servicio.
);

export default eventoExternoClient;