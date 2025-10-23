package org.empuje_comunitario.graphql_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "voluntarios_externos", uniqueConstraints = {@UniqueConstraint(columnNames = {"id_voluntario_origen", "id_organizacion"})})
public class VoluntarioExterno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVoluntarioExterno;

    @Column(name = "id_voluntario_origen", nullable = false)
    private String idVoluntarioOrigen;

    @Column(name="nombre", nullable = false)
    private String nombre;

    @Column(name="apellido", nullable = false)
    private String apellido;

    @Column(name="telefono", nullable = false)
    private String telefono;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="id_organizacion", nullable = false)
    private String idOrganizacion;
}