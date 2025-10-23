package org.empuje_comunitario.graphql_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.DataFetcherExceptionResolver;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;

@Configuration
public class GraphQLExceptionConfig {

    @Bean
    DataFetcherExceptionResolver exceptionResolver() {
        return new DataFetcherExceptionResolverAdapter() {
            @Override
            protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
                //Devolver el mensaje de la excepción como error de GraphQL:
                return GraphqlErrorBuilder.newError()
                        .message(ex.getMessage())
                        .path(env.getExecutionStepInfo().getPath())
                        .build();
            }
        };
    }
}