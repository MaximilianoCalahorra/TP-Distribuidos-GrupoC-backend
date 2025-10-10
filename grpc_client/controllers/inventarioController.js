import * as grpc from '@grpc/grpc-js';
import inventarioClient from '../client/inventarioClient.js';
import { handleGrpcResponse } from '../utils/handleGrpcResponse.js';

//Listar inventarios:
export const listarInventarios = (req, res) => {

   const md = new grpc.Metadata();
   const auth = req.get('Authorization');
   if (auth) md.add('Authorization', auth);

  //El cliente gRPC llama al método correspondiente en el servidor:
  inventarioClient.ListarInventarios({}, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Listar inventarios activos:
export const listarInventariosActivos = (req, res) => {

  const md = new grpc.Metadata();
  const auth = req.get('Authorization');
  if (auth) md.add('Authorization', auth);

  //El cliente gRPC llama al método correspondiente en el servidor:
  inventarioClient.ListarInventariosActivos({}, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Crear un inventario:
export const crearInventario = (req, res) => {
  const { categoria, descripcion, cantidad } = req.body;

  const CategoriaEnum = {
    DESCONOCIDA: 0,
    ROPA: 1,
    ALIMENTOS: 2,
    JUGUETES: 3,
    UTILES_ESCOLARES: 4
  };

  const crearInventarioProto = {
    categoria: CategoriaEnum[categoria.toUpperCase()] || 0,
    descripcion,
    cantidad
  };

   const md = new grpc.Metadata();
   const auth = req.get('Authorization');
   if (auth) md.add('Authorization', auth);

  inventarioClient.CrearInventario(crearInventarioProto, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Modificar un inventario:
export const modificarInventario = (req, res) => {
  const { descripcion, cantidad } = req.body;
  const idInventario = req.params.id;

  const modificarInventarioProto =  {
    idInventario,
    descripcion,
    cantidad
  }

  const md = new grpc.Metadata();
  const auth = req.get('Authorization');
  if (auth) md.add('Authorization', auth);

  inventarioClient.ModificarInventario(modificarInventarioProto, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Eliminar lógicamente un inventario:
export const eliminarLogicoInventario = (req, res) => {
  const idInventario = req.params.id;

  const IdInventarioRequestProto = {
    idInventario
  }

  const md = new grpc.Metadata();
  const auth = req.get('Authorization');
  if (auth) md.add('Authorization', auth);

  inventarioClient.EliminarLogicoInventario(IdInventarioRequestProto, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Habilitar un inventario:
export const habilitarInventario = (req, res) => {
  const idInventario = req.params.id;

  const IdInventarioRequestProto = {
    idInventario
  }

  const md = new grpc.Metadata();
  const auth = req.get('Authorization');
  if (auth) md.add('Authorization', auth);

  inventarioClient.HabilitarInventario(IdInventarioRequestProto, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Traer un inventario:
export const traerInventario = (req, res) => {

  const idInventario = req.params.id;

   const IdInventarioRequestProto = {
      idInventario
   }

   const md = new grpc.Metadata();
   const auth = req.get('Authorization');
   if (auth) md.add('Authorization', auth);

  //El cliente gRPC llama al método correspondiente en el servidor:
  inventarioClient.traerInventario(IdInventarioRequestProto, md, (error, response) => handleGrpcResponse(res, error, response));
};