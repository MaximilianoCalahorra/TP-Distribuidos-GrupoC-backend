import rolClient from '../client/rolClient.js';
import { handleGrpcResponse } from '../utils/handleGrpcResponse.js';

//Listar roles:
export const listarRoles = (req, res) => {
  //El cliente gRPC llama al método correspondiente en el servidor:
  rolClient.ListarRoles({}, (error, response) => handleGrpcResponse(res, error, response));
};