import * as grpc from '@grpc/grpc-js';
import eventoSolidarioClient from '../client/eventoSolidarioClient.js';
import { handleGrpcResponse } from '../utils/handleGrpcResponse.js';
import { parseDatetimeToProto } from '../utils/timestampMapper.js';

//Listar eventos solidarios:
export const listarEventosSolidarios = (req, res) => {

    const md = new grpc.Metadata();
    const auth = req.get('authorization');
    if (auth) md.add('authorization', auth);

    //El cliente gRPC llama al metodo correspondiente en el servidor:
    eventoSolidarioClient.ListarEventosSolidarios({}, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Crear un evento solidario:
export const crearEventoSolidario = (req, res) => {
    const { nombre, descripcion, fechaHora, miembros } = req.body;

    const crearEventoSolidarioProto = {
        nombre,
        descripcion,
        fechaHora,
        miembros
    };

    const md = new grpc.Metadata();
    const auth = req.get('authorization');
    if (auth) md.add('authorization', auth);

    eventoSolidarioClient.CrearEventoSolidario(crearEventoSolidarioProto, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Modificar un evento solidario:
export const modificarEventoSolidario = (req, res) => {
    const { nombre, descripcion, fechaHora, miembros } = req.body;
    const idEventoSolidario = req.params.id;

    const modificarEventoSolidarioProto =  {
        idEventoSolidario,
        nombre,
        descripcion,
        fechaHora,
        miembros
    }

    const md = new grpc.Metadata();
    const auth = req.get('authorization');
    if (auth) md.add('authorization', auth);

    eventoSolidarioClient.ModificarEventoSolidario(modificarEventoSolidarioProto, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Eliminar un evento solidario:
export const eliminarEventoSolidario = (req, res) => {
    const idEventoSolidario = req.params.id;

    const IdEventoSolidarioRequestProto = {
        idEventoSolidario
    }

    const md = new grpc.Metadata();
    const auth = req.get('authorization');
    if (auth) md.add('authorization', auth);

    eventoSolidarioClient.EliminarEventoSolidario(IdEventoSolidarioRequestProto, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Participar de un evento solidario:
export const participarDeEventoSolidario = (req, res) => {
    const idEventoSolidario = req.params.id;

    const IdEventoSolidarioRequestProto = {
        idEventoSolidario
    }

    const md = new grpc.Metadata();
    const auth = req.get('authorization');
    if (auth) md.add('authorization', auth);

    eventoSolidarioClient.ParticiparDeEventoSolidario(IdEventoSolidarioRequestProto, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Darse de baja de un evento solidario:
export const darseDeBajaDeEventoSolidario = (req, res) => {
    const idEventoSolidario = req.params.id;

    const IdEventoSolidarioRequestProto = {
        idEventoSolidario
    }

    const md = new grpc.Metadata();
    const auth = req.get('authorization');
    if (auth) md.add('authorization', auth);

    eventoSolidarioClient.DarseDeBajaDeEventoSolidario(IdEventoSolidarioRequestProto, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Obtener un evento solidario por ID:
export const traerEventoSolidarioPorId = (req, res) => {
    const idEventoSolidario = req.params.id;

    const IdEventoSolidarioRequestProto = {
        idEventoSolidario
    }

     const md = new grpc.Metadata();
     const auth = req.get('authorization');
     if (auth) md.add('authorization', auth);

    eventoSolidarioClient.traerEventoSolidarioPorId(IdEventoSolidarioRequestProto, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Publicar evento solidario:
export const publicarEventoSolidario = (req, res) => {

    const idEventoSolidario = req.params.id;

    const IdEventoSolidarioRequestProto = {
        idEventoSolidario
    }

    const md = new grpc.Metadata();
    const auth = req.get('authorization');
    if (auth) md.add('authorization', auth);

    eventoSolidarioClient.publicarEventoSolidario(IdEventoSolidarioRequestProto, md, (error, response) => handleGrpcResponse(res, error, response));
}