import { createGrpcClient } from "../utils/grpcClientFactory.js";

const solicitudDonacionClient = createGrpcClient(
  "../proto/services/solicitud_donacion_service.proto", //Ruta al proto.
  "SolicitudDonacionService" //Nombre del servicio.
);

export default solicitudDonacionClient;