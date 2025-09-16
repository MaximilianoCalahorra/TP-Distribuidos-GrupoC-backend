import grpc from '@grpc/grpc-js';

//Mapeador de errores gRPC a HTTP:
export const mapGrpcErrorToHttp = (error) => {
  if (!error || !error.code) return { status: 500, message: 'Error interno desconocido' }; //El error llegó vacío o sin código, estado 500.

  //Según el código de error:
  switch (error.code) {
    case grpc.status.INVALID_ARGUMENT:
      return { status: 400, message: error.details }; //400 - Argumento inválido.
    case grpc.status.NOT_FOUND:
      return { status: 404, message: error.details }; //404 - No encontrado.
    case grpc.status.FAILED_PRECONDITION:
      return { status: 409, message: error.details }; //409 - Conflicto con el estado actual del recurso.
    case grpc.status.INTERNAL:
      return { status: 500, message: error.details }; //500 - Error interno del servidor.
    default:
      return { status: 500, message: 'Error interno del servidor' };
  }
};