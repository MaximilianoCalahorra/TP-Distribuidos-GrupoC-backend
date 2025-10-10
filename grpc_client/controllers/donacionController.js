import * as grpc from '@grpc/grpc-js';
import donacionClient from '../client/donacionClient.js';
import { handleGrpcResponse } from '../utils/handleGrpcResponse.js';

// Listar donaciones por evento
export const listarDonacionesPorEvento = (req, res) => {
  const idEvento = req.params.id;

  const IdEventoRequestProto = {
    idEvento
  };

  const md = new grpc.Metadata();
  const auth = req.get('authorization');
  if (auth) md.add('authorization', auth);

  donacionClient.ListarDonacionesPorEvento(IdEventoRequestProto, md, (error, response) => handleGrpcResponse(res, error, response));
};

// Crear una donación
export const crearDonacion = (req, res) => {
  const { cantidad, idInventario } = req.body;
  const idEventoSolidario = req.params.id;

  const CrearDonacionProto = {
    idEventoSolidario,
    cantidad,
    idInventario
  };

  const md = new grpc.Metadata();
  const auth = req.get('authorization');
  if (auth) md.add('authorization', auth);

  donacionClient.CrearDonacion(CrearDonacionProto, md, (error, response) => handleGrpcResponse(res, error, response));
};