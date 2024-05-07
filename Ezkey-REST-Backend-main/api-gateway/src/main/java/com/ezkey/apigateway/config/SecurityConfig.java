package com.ezkey.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/users/**",
            "/eureka/**",
            "/projects/**",
            "/webjars/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/**"
    };

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity
                .cors(Customizer.withDefaults())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanger -> exchanger
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .pathMatchers("/api/**").authenticated()
                        .pathMatchers(AUTH_WHITELIST).permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                        .jwt(jwtSpec -> jwtSpec
                                .jwtAuthenticationConverter(new KeycloakJwtRolesConverter())));
        return serverHttpSecurity.build();
    }
}
