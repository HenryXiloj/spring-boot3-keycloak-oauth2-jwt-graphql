package com.henry.springboot3security6oauth2jwtgraphql.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQlConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer(ObjectMapper objectMapper) {
        GraphQLScalarType jsonScalarType = GraphQLScalarType.newScalar()
                .name("JSON")
                .description("A JSON scalar")
                .coercing(new JsonNodeCoercing(objectMapper))
                .build();

        return wiringBuilder -> wiringBuilder
                .scalar(jsonScalarType);
    }
}