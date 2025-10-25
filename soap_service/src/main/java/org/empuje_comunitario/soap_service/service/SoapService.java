package org.empuje_comunitario.soap_service.service;

import org.empuje_comunitario.soap_service.wsdl.Application;
import org.empuje_comunitario.soap_service.wsdl.StringArray;
import org.empuje_comunitario.soap_service.wsdl.OrganizationTypeArray;
import org.empuje_comunitario.soap_service.wsdl.PresidentTypeArray;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoapService {
	///Atributo:
    private final Application soapClient;

    ///Constructor:
    public SoapService(Application soapClient) {
        this.soapClient = soapClient;
    }

    ///Obtener listado de ONGs solicitadas:
    public OrganizationTypeArray listAssociations(List<String> orgIds) {
        StringArray ids = new StringArray();
        ids.getString().addAll(orgIds);
        return soapClient.listAssociations(ids);
    }

    ///Obtener listado de presidentes de las ONGs solicitadas:
    public PresidentTypeArray listPresidents(List<String> orgIds) {
        StringArray ids = new StringArray();
        ids.getString().addAll(orgIds);
        return soapClient.listPresidents(ids);
    }
}