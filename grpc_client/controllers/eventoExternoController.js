import * as grpc from '@grpc/grpc-js';
import eventoExternoClient from '../client/eventoExternoClient.js';
import { handleGrpcResponse } from '../utils/handleGrpcResponse.js';

//Adherir participante interno:
export const adherirParticipanteInterno = (req, res) => {

  const { idEvento, idOrganizador , emailParticipante } = req.body;

  const adhesionParticipanteInternoRequestProto = {
    idEvento,
    idOrganizador,
    emailParticipante
  };

  const md = new grpc.Metadata();
  const auth = req.get('authorization');
  if (auth) md.add('authorization', auth);

  //El cliente gRPC llama al metodo correspondiente en el servidor:
  eventoExternoClient.AdherirParticipanteInterno(adhesionParticipanteInternoRequestProto, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Listar eventos externos:
export const listarEventosExternos = (req, res) => {

    const md = new grpc.Metadata();
    const auth = req.get('authorization');
    if (auth) md.add('authorization', auth);

    //El cliente gRPC llama al metodo correspondiente en el servidor:
    eventoExternoClient.ListarEventosExternos({}, md, (error, response) => handleGrpcResponse(res, error, response));
};