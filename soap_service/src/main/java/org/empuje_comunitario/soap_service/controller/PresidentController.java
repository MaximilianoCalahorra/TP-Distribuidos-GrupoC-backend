package org.empuje_comunitario.soap_service.controller;

import java.util.List;

import org.empuje_comunitario.soap_service.dto.PresidentDTO;
import org.empuje_comunitario.soap_service.service.SoapService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/soap/presidentes")
public class PresidentController {
	///Atributo:
    private final SoapService soapService;

    ///Constructor:
    public PresidentController(SoapService soapService) {
        this.soapService = soapService;
    }

    ///Listar presidentes de las ONGs indicadas:
    @PostMapping
    public List<PresidentDTO> listar(@RequestBody List<String> orgIds) {
        var presidents = soapService.listPresidents(orgIds).getPresidentType(); //Obtenemos los presidentes del servidor SOAP.

        //Retonarmos cada presidente mapeado a nuestra clase:
        return presidents.stream()
                .map(p -> new PresidentDTO(
                        p.getId().getValue().longValue(),
                        p.getName().getValue(),
                        p.getAddress().getValue(),
                        p.getPhone().getValue(),
                        p.getOrganizationId().getValue().longValue()
                ))
                .toList();
    }
}
