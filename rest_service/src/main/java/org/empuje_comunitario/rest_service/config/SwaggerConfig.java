package org.empuje_comunitario.rest_service.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI empujeComunitarioOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Empuje Comunitario - API REST - Grupo C")
                        .description("API REST para informes y filtros personalizados de donaciones y eventos")
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositorio del proyecto")
                        .url("https://github.com/MaximilianoCalahorra/TP-Distribuidos-GrupoC-backend"))
				        .addSecurityItem(new SecurityRequirement().addList("basicAuth"))
				        .components(new Components()
				                .addSecuritySchemes("basicAuth",
				                        new SecurityScheme()
				                                .name("basicAuth")
				                                .type(SecurityScheme.Type.HTTP)
				                                .scheme("basic")));
    }
}
