import * as grpc from '@grpc/grpc-js';
import transferenciaDonacionClient from '../client/transferenciaDonacionClient.js';
import { handleGrpcResponse } from '../utils/handleGrpcResponse.js';

//Listar transferencias de donaciones de nuestra ONG:
export const listarTransferenciasDonaciones = (req, res) => {

  const md = new grpc.Metadata();
  const auth = req.get('Authorization');
  if (auth) md.add('Authorization', auth);

  //El cliente gRPC llama al método correspondiente en el servidor:
  transferenciaDonacionClient.ListarTransferenciasDonaciones({}, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Listar transferencias de donaciones salientes de nuestra ONG:
export const listarTransferenciasDonacionesSalientes = (req, res) => {

  const md = new grpc.Metadata();
  const auth = req.get('Authorization');
  if (auth) md.add('Authorization', auth);

  //El cliente gRPC llama al método correspondiente en el servidor:
  transferenciaDonacionClient.ListarTransferenciasDonacionesSalientes({}, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Listar transferencias de donaciones entrantes de nuestra ONG:
export const listarTransferenciasDonacionesEntrantes = (req, res) => {

  const md = new grpc.Metadata();
  const auth = req.get('Authorization');
  if (auth) md.add('Authorization', auth);

  //El cliente gRPC llama al método correspondiente en el servidor:
  transferenciaDonacionClient.ListarTransferenciasDonacionesEntrantes({}, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Crear transferencia de donación saliente:
export const crearTransferenciaDonacionSaliente = (req, res) => {
  
  const { idSolicitudDonacion, idOrganizacionReceptora, items } = req.body;

  //Mapear items a ItemDonacionProto:
  const itemsProto = (items || []).map(item => ({
    categoria: item.categoria || "",
    descripcion: item.descripcion || "",
    cantidad: item.cantidad || 0
  }));

  //Construir TransferenciaDonacionProto:
  const transferenciaDonacionProto = {
    idSolicitudDonacion: idSolicitudDonacion,
    idOrganizacionReceptora: idOrganizacionReceptora,
    items: itemsProto
  };
  
  const md = new grpc.Metadata();
  const auth = req.get('Authorization');
  if (auth) md.add('Authorization', auth);

  //El cliente gRPC llama al método correspondiente en el servidor:
  transferenciaDonacionClient.CrearTransferenciaSaliente(transferenciaDonacionProto, md, (error, response) => handleGrpcResponse(res, error, response));
}