import donacionClient from '../client/donacionClient.js';
import { handleGrpcResponse } from '../utils/handleGrpcResponse.js';

// Listar donaciones por evento
export const listarDonacionesPorEvento = (req, res) => {
  const idEvento = req.params.id;

  const IdEventoRequestProto = {
    idEvento
  };

  donacionClient.ListarDonacionesPorEvento(IdEventoRequestProto, (error, response) => handleGrpcResponse(res, error, response));
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

  donacionClient.CrearDonacion(CrearDonacionProto, (error, response) => handleGrpcResponse(res, error, response));
};