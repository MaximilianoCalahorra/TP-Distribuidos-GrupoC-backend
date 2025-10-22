import { createGrpcClient } from "../utils/grpcClientFactory.js";

const ofertaDonacionClient = createGrpcClient(
  "../proto/services/oferta_donacion_service.proto", //Ruta al proto.
  "OfertaDonacionService" //Nombre del servicio.
);

export default ofertaDonacionClient;