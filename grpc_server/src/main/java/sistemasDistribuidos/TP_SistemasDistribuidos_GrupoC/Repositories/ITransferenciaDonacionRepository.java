package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.TransferenciaDonacion;

@Repository
public interface ITransferenciaDonacionRepository extends JpaRepository<TransferenciaDonacion, Long> {
	///Transferencias salientes de una organización:
    public List<TransferenciaDonacion> findByIdOrganizacionDonante(String idOrganizacionDonante);
    
    ///Transferencias entrantes de una organización:
    public List<TransferenciaDonacion> findByIdOrganizacionReceptora(String idOrganizacionReceptora);
}
