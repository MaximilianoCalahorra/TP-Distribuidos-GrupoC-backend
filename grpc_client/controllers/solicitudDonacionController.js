import * as grpc from '@grpc/grpc-js';
import solicitudDonacionClient from '../client/solicitudDonacionClient.js';
import { handleGrpcResponse } from '../utils/handleGrpcResponse.js';

//Listar solicitudes de donaciones internas:
export const listarSolicitudesDonacionesInternas = (req, res) => {

  const md = new grpc.Metadata();
  const auth = req.get('Authorization');
  if (auth) md.add('Authorization', auth);

  //El cliente gRPC llama al método correspondiente en el servidor:
  solicitudDonacionClient.ListarSolicitudesDonacionesInternas({}, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Listar solicitudes de donaciones externas:
export const listarSolicitudesDonacionesExternas = (req, res) => {

  const md = new grpc.Metadata();
  const auth = req.get('Authorization');
  if (auth) md.add('Authorization', auth);

  //El cliente gRPC llama al método correspondiente en el servidor:
  solicitudDonacionClient.ListarSolicitudesDonacionesExternas({}, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Crear solicitud de donación interna:
export const crearSolicitudDonacionInterna = (req, res) => {
  
  const { items } = req.body;

  //Mapear items a ItemDonacionProto:
  const itemsProto = (items || []).map(item => ({
    categoria: item.categoria || "",
    descripcion: item.descripcion || ""
  }));

  //Construir SolicitudDonacionProto:
  const solicitudDonacionProto = {
    idSolicitudDonacion: 0,
    idSolicitudDonacionOrigen: "",
    idOrganizacion: "",
    items: itemsProto
  };
  
  const md = new grpc.Metadata();
  const auth = req.get('Authorization');
  if (auth) md.add('Authorization', auth);

  //El cliente gRPC llama al método correspondiente en el servidor:
  solicitudDonacionClient.CrearSolicitudDonacionInterna(solicitudDonacionProto, md, (error, response) => handleGrpcResponse(res, error, response));
}

//Procesar baja de solicitud de donación interna:
export const procesarBajaSolicitudDonacionInterna = (req, res) => {
  
  const idSolicitud = req.params.id; //Obtener id de la solicitud.

  //Construir el proto:
  const BajaSolicitudDonacionKafkaProto = {
      idSolicitud
  }
  
  const md = new grpc.Metadata();
  const auth = req.get('Authorization');
  if (auth) md.add('Authorization', auth);

  //El cliente gRPC llama al método correspondiente en el servidor:
  solicitudDonacionClient.ProcesarBajaSolicitudDonacion(BajaSolicitudDonacionKafkaProto, md, (error, response) => handleGrpcResponse(res, error, response));
}