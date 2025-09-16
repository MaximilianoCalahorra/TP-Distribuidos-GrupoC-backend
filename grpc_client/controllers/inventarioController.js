import inventarioClient from '../client/inventarioClient.js';
import { handleGrpcResponse } from '../utils/handleGrpcResponse.js';

//Listar inventarios:
export const listarInventarios = (req, res) => {
  //El cliente gRPC llama al método correspondiente en el servidor:
  inventarioClient.ListarInventarios({}, (error, response) => handleGrpcResponse(res, error, response));
};

//Listar inventarios activos:
export const listarInventariosActivos = (req, res) => {
  //El cliente gRPC llama al método correspondiente en el servidor:
  inventarioClient.ListarInventariosActivos({}, (error, response) => handleGrpcResponse(res, error, response));
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

  inventarioClient.CrearInventario(crearInventarioProto, (error, response) => handleGrpcResponse(res, error, response));
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

  inventarioClient.ModificarInventario(modificarInventarioProto, (error, response) => handleGrpcResponse(res, error, response));
};

//Eliminar lógicamente un inventario:
export const eliminarLogicoInventario = (req, res) => {
  const idInventario = req.params.id;

  const IdInventarioRequestProto = {
    idInventario
  }

  inventarioClient.EliminarLogicoInventario(IdInventarioRequestProto, (error, response) => handleGrpcResponse(res, error, response));
};

//Habilitar un inventario:
export const habilitarInventario = (req, res) => {
  const idInventario = req.params.id;

  const IdInventarioRequestProto = {
    idInventario
  }

  inventarioClient.HabilitarInventario(IdInventarioRequestProto, (error, response) => handleGrpcResponse(res, error, response));
};