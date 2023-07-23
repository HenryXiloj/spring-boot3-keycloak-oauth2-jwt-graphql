package com.henry.springboot3security6oauth2jwtgraphql.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.henry.springboot3security6oauth2jwtgraphql.dto.JWTTokenDto;
import com.henry.springboot3security6oauth2jwtgraphql.resolver.Resolver;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Controller;

@Controller
public class DefaultJwtController {

    private final Resolver resolver;

    public DefaultJwtController(Resolver resolver) {
        this.resolver = resolver;
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public String getName(JwtAuthenticationToken jwt) throws JsonProcessingException {
        return resolver.getName(jwt);
    }

    @QueryMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public JWTTokenDto getJWTByUser(JwtAuthenticationToken jwt) throws JsonProcessingException {
        return resolver.getJWTByUser(jwt);
    }
}
