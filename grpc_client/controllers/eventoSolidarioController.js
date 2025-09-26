import * as grpc from '@grpc/grpc-js';
import eventoSolidarioClient from '../client/eventoSolidarioClient.js';
import { handleGrpcResponse } from '../utils/handleGrpcResponse.js';
import { parseDatetimeToProto } from '../utils/timestampMapper.js';

//Listar eventos solidarios:
export const listarEventosSolidarios = (req, res) => {

    const md = new grpc.Metadata();
    const auth = req.get('authorization');
    if (auth) md.add('authorization', auth);

    //El cliente gRPC llama al mÃ©todo correspondiente en el servidor:
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

    console.log(crearEventoSolidarioProto.fechaHora);

    eventoSolidarioClient.CrearEventoSolidario(crearEventoSolidarioProto, md, (error, response) => handleGrpcResponse(res, error, response));
};

//Modificar un evento solidario:
export const modificarEventoSolidario = (req, res) => {
    const { nombre, fechaHora, miembros } = req.body;
    const idEventoSolidario = req.params.id;

    const modificarEventoSolidarioProto =  {
        idEventoSolidario,
        nombre,
        fechaHora: parseDatetimeToProto(fechaHora),
        miembros
    }

    eventoSolidarioClient.ModificarEventoSolidario(modificarEventoSolidarioProto, (error, response) => handleGrpcResponse(res, error, response));
};

//Eliminar un evento solidario:
export const eliminarEventoSolidario = (req, res) => {
    const idEventoSolidario = req.params.id;

    const IdEventoSolidarioRequestProto = {
        idEventoSolidario
    }

    eventoSolidarioClient.EliminarEventoSolidario(IdEventoSolidarioRequestProto, (error, response) => handleGrpcResponse(res, error, response));
};