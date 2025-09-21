import { createGrpcClient } from "../utils/grpcClientFactory.js";

const usuarioClient = createGrpcClient(
  "../proto/services/usuario_service.proto", //Ruta al proto.
  "UsuarioService" //Nombre del servicio.
);

export default usuarioClient;