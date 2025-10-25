package org.empuje_comunitario.soap_service.controller;

import java.util.List;

import org.empuje_comunitario.soap_service.dto.OrganizationDTO;
import org.empuje_comunitario.soap_service.service.SoapService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/soap/ongs")
public class OrganizationController {
	///Atributo:
    private final SoapService soapService;

    ///Constructor:
    public OrganizationController(SoapService soapService) {
        this.soapService = soapService;
    }

    ///Listar ONGs indicadas:
    @PostMapping
    public List<OrganizationDTO> listar(@RequestBody List<String> orgIds) {
        var orgs = soapService.listAssociations(orgIds).getOrganizationType(); //Obtenemos las ONGs del servidor SOAP.

        //Retornamos cada ONG mapeada a nuestra clase:
        return orgs.stream()
                .map(o -> new OrganizationDTO(
                        o.getId().getValue().longValue(),
                        o.getName().getValue(),
                        o.getAddress().getValue(),
                        o.getPhone().getValue()
                ))
                .toList();
    }
}
