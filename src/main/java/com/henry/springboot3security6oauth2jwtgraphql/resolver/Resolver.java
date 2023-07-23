package com.henry.springboot3security6oauth2jwtgraphql.resolver;


import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.henry.springboot3security6oauth2jwtgraphql.dto.JWTTokenDto;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Resolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    public static ObjectMapper objectMapper = new ObjectMapper();

    public String getName(JwtAuthenticationToken jwt) throws JsonProcessingException {
        objectMapper.findAndRegisterModules();
        var respData = objectMapper.writeValueAsString(jwt.getTokenAttributes());

        var user = objectMapper.readValue(respData, JWTTokenDto.class);

        return user.getName();
    }

    public JWTTokenDto getJWTByUser(JwtAuthenticationToken jwt) throws JsonProcessingException {
        objectMapper.findAndRegisterModules();
        var respData = objectMapper.writeValueAsString(jwt.getTokenAttributes());

        var user = objectMapper.readValue(respData, JWTTokenDto.class);
        return user;
    }
}
