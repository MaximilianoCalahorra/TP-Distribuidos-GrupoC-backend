package org.empuje_comunitario.soap_service.dto;

public record PresidentDTO(
        long id,
        String name,
        String address,
        String phone,
        long organizationId
) {}
