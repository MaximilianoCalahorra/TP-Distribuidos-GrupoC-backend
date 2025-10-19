package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Services.Interfaces;

import java.util.List;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.DTOs.TransferenciaDonacionDTO;

public interface ITransferenciaDonacionService {
	///Transferencias de nuestra ONG:
    public List<TransferenciaDonacionDTO> listarTransferencias();
    
    ///Transferencias salientes de nuestra ONG:
    public List<TransferenciaDonacionDTO> listarTransferenciasSalientes(String idOrganizacionDonante);
    
    ///Transferencias entrantes de nuestra ONG:
    public List<TransferenciaDonacionDTO> listarTransferenciasEntrantes(String idOrganizacionReceptora);
    
    ///Crear transferencia saliente:
    public void crearTransferenciaSaliente(TransferenciaDonacionDTO transferencia);
    
    ///Crear transferencia entrante:
    public void crearTransferenciaEntrante(TransferenciaDonacionDTO transferencia);
}
