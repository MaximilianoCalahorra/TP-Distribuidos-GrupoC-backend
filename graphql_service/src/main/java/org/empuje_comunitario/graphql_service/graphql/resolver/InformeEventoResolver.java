package org.empuje_comunitario.graphql_service.graphql.resolver;

import java.util.List;

import org.empuje_comunitario.graphql_service.graphql.model.InformeEventoMesGraphQL;
import org.empuje_comunitario.graphql_service.service.interfaces.IInformeEventoService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class InformeEventoResolver {
	///Atributo:
    private final IInformeEventoService informeEventoService;

    ///Resolvers:
    
    //Obtener informe de participación en eventos de la ONG agrupados por mes:
    @QueryMapping("informeParticipacionEventos")
    public List<InformeEventoMesGraphQL> informeParticipacionEventos(@Argument("filtroJson") String filtroJson) {
        return informeEventoService.informeParticipacionEventos(filtroJson);
    }
}
