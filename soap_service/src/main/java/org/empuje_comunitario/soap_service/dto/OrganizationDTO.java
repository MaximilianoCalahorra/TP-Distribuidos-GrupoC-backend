package org.empuje_comunitario.soap_service.dto;

public record OrganizationDTO(
        long id,
        String name,
        String address,
        String phone
) {}
