import * as grpc from '@grpc/grpc-js';
import usuarioClient from '../client/usuarioClient.js';
import { handleGrpcResponse } from '../utils/handleGrpcResponse.js';

//Crear un usuario:
export const crearUsuario = (req, res) => {
   const {nombreUsuario, nombre, apellido, email, telefono, rol} = req.body;

   const crearUsuarioProto = {
    nombreUsuario,
    nombre,
    apellido,
    email,
    telefono,
    rol
   }

   const md = new grpc.Metadata();
   const auth = req.get('authorization');
   if (auth) md.add('authorization', auth);

  //El cliente gRPC llama al método correspondiente en el servidor:
  usuarioClient.crearUsuario(crearUsuarioProto, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Login:
export const login = (req, res) => {

  const { nombreUsuario, clave } = req.body;

  const loginUsuarioProto = {
    nombreUsuario,
    clave
  }

  //El cliente gRPC llama al método correspondiente en el servidor:
  usuarioClient.login(loginUsuarioProto, (error, response) => handleGrpcResponse(res, error, response));
};

//Descativar un usuario:
export const desactivarUsuario = (req, res) => {

   const idUsuario = req.params.id;

   const IdUsuarioRequestProto = {
    idUsuario
   }

   const md = new grpc.Metadata();
   const auth = req.get('Authorization');
   if (auth) md.add('Authorization', auth);

  //El cliente gRPC llama al método correspondiente en el servidor:
  usuarioClient.desactivarUsuario(IdUsuarioRequestProto, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Modificar un usuario:
export const modificarUsuario = (req, res) => {
   const idUsuario = req.params.id;
   const { nombreUsuario, nombre, apellido, email, telefono, rol } = req.body;

   const modificarUsuarioProto = {
    idUsuario,
    nombreUsuario,
    nombre,
    apellido,
    email,
    telefono,
    rol
   }

   const md = new grpc.Metadata();
   const auth = req.get('authorization');
   if (auth) md.add('authorization', auth);

   //El cliente gRPC llama al método correspondiente en el servidor:
   usuarioClient.modificarUsuario(modificarUsuarioProto, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Listar usuarios:
export const listarUsuarios = (req, res) => {

    const md = new grpc.Metadata();
    const auth = req.get('Authorization');
    if (auth) md.add('Authorization', auth);

  //El cliente gRPC llama al método correspondiente en el servidor:
  usuarioClient.listarUsuarios({}, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Traer un usuario:
export const traerUsuario = (req, res) => {

  const idUsuario = req.params.id;

   const IdUsuarioRequestProto = {
      idUsuario
   }

  //El cliente gRPC llama al método correspondiente en el servidor:
  usuarioClient.traerUsuario(IdUsuarioRequestProto, (error, response) => handleGrpcResponse(res, error, response));
};

//Reactivar un usuario:
export const  reactivarUsuario = (req, res) => {

   const idUsuario = req.params.id;

   const IdUsuarioRequestProto = {
    idUsuario
   }

   const md = new grpc.Metadata();
   const auth = req.get('Authorization');
   if (auth) md.add('Authorization', auth);

  //El cliente gRPC llama al método correspondiente en el servidor:
  usuarioClient.reactivarUsuario(IdUsuarioRequestProto, md, (error, response) => handleGrpcResponse(res, error, response));
};

