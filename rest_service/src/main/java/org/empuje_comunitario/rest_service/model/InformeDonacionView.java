package org.empuje_comunitario.rest_service.model;

import jakarta.persistence.*;
import org.empuje_comunitario.rest_service.enums.Categoria;
import org.empuje_comunitario.rest_service.enums.Operacion;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vw_informe_donaciones")
public class InformeDonacionView {

    @Id
    @Column(name = "id_fila")
    private Long idFila;
    @Column(name = "id_transferencia")
    private Long idTransferencia;
    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;
    @Column(name = "fecha_alta")
    private LocalDate fechaAlta;

    private String descripcion;
    private Integer cantidad;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    @Enumerated(EnumType.STRING)
    private Operacion operacion;

    private Boolean eliminado;

    @Column(name = "org_donante")
    private String orgDonante;
    @Column(name = "org_receptora")
    private String orgReceptora;
    @Column(name = "id_solicitud")
    private String idSolicitud;
    @Column(name = "usuario_alta")
    private String usuarioAlta;
    @Column(name = "usuario_modificacion")
    private String usuarioModificacion;

    @Column(name = "rol_usuario_alta")
    private String rolUsuarioAlta;
    @Column(name = "rol_usuario_modificacion")
    private String rolUsuarioModificacion;

    public Long getIdFila() {
        return idFila;
    }

    public void setIdFila(Long idFila) {
        this.idFila = idFila;
    }

    public Long getIdTransferencia() {
        return idTransferencia;
    }

    public void setIdTransferencia(Long idTransferencia) {
        this.idTransferencia = idTransferencia;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Operacion getOperacion() {
        return operacion;
    }

    public void setOperacion(Operacion operacion) {
        this.operacion = operacion;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    public String getOrgDonante() {
        return orgDonante;
    }

    public void setOrgDonante(String orgDonante) {
        this.orgDonante = orgDonante;
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getUsuarioAlta() {
        return usuarioAlta;
    }

    public void setUsuarioAlta(String usuarioAlta) {
        this.usuarioAlta = usuarioAlta;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public String getRolUsuarioAlta() {
        return rolUsuarioAlta;
    }

    public void setRolUsuarioAlta(String rolUsuarioAlta) {
        this.rolUsuarioAlta = rolUsuarioAlta;
    }

    public String getRolUsuarioModificacion() {
        return rolUsuarioModificacion;
    }

    public void setRolUsuarioModificacion(String rolUsuarioModificacion) {
        this.rolUsuarioModificacion = rolUsuarioModificacion;
    }

    public String getOrgReceptora() {
        return orgReceptora;
    }

    public void setOrgReceptora(String orgReceptora) {
        this.orgReceptora = orgReceptora;
    }
}
