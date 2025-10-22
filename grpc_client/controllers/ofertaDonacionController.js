import * as grpc from '@grpc/grpc-js';
import ofertaDonacionClient from '../client/ofertaDonacionClient.js';
import { handleGrpcResponse } from '../utils/handleGrpcResponse.js';

//Listar ofertas de donaciones internas:
export const listarOfertasDonacionesInternas = (req, res) => {

  const md = new grpc.Metadata();
  const auth = req.get('Authorization');
  if (auth) md.add('Authorization', auth);

  //El cliente gRPC llama al método correspondiente en el servidor:
  ofertaDonacionClient.ListarOfertasDonacionesInternas({}, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Listar ofertas de donaciones externas:
export const listarOfertasDonacionesExternas = (req, res) => {

  const md = new grpc.Metadata();
  const auth = req.get('Authorization');
  if (auth) md.add('Authorization', auth);

  //El cliente gRPC llama al método correspondiente en el servidor:
  ofertaDonacionClient.ListarOfertasDonacionesExternas({}, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Crear oferta de donación interna:
export const crearOfertaDonacionInterna = (req, res) => {
  
  const { items } = req.body;

  //Mapear items a ItemDonacionProto:
  const itemsProto = (items || []).map(item => ({
    categoria: item.categoria || "",
    descripcion: item.descripcion || "",
    cantidad: item.cantidad || 0
  }));

  //Construir OfertaDonacionProto:
  const OfertaDonacionProto = {
    idOfertaDonacion: 0,
    idOfertaDonacionOrigen: "",
    idOrganizacion: "",
    items: itemsProto
  };
  
  const md = new grpc.Metadata();
  const auth = req.get('Authorization');
  if (auth) md.add('Authorization', auth);

  //El cliente gRPC llama al método correspondiente en el servidor:
  ofertaDonacionClient.CrearOfertaDonacionInterna(OfertaDonacionProto, md, (error, response) => handleGrpcResponse(res, error, response));
}