import { mapGrpcErrorToHttp } from "./mapGrpcErrorAHttp.js";

//Manejador de respuesta gRPC:
export const handleGrpcResponse = (res, error, response) => {
  //Si hay algún error:
  if (error) {
    const { status, message } = mapGrpcErrorAHttp(error); //Obtenemos estado y mensaje.
    return res.status(status).json({ error: message }); //Retornamos el estado y el mensaje.
  }
  return res.json(response); //Si no hay errores, retornamos la respuesta del servidor gRPC.
}