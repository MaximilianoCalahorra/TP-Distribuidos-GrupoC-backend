package sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sistemasDistribuidos.TP_SistemasDistribuidos_GrupoC.Models.TransferenciaDonacion;

@Repository
public interface ITransferenciaDonacionRepository extends JpaRepository<TransferenciaDonacion, Long> {

    @Query("SELECT td FROM TransferenciaDonacion td LEFT JOIN FETCH td.items LEFT JOIN FETCH td.usuarioAlta LEFT JOIN FETCH td.usuarioModificacion " +
            "WHERE td.idOrganizacionDonante = :idOrganizacionDonante  ")
    public List<TransferenciaDonacion> findByIdOrganizacionDonante(@Param("idOrganizacionDonante") String idOrganizacionDonante);
    
    ///Transferencias entrantes de una organización:
    public List<TransferenciaDonacion> findByIdOrganizacionReceptora(String idOrganizacionReceptora);
}
