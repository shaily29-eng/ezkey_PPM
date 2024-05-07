package com.ezkey.userservice.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class KeycloakJwtRolesConverter implements Converter<Jwt, Mono<AbstractAuthenticationToken>> {

    private static final String CLAIM_REALM_ACCESS = "realm_access";
    private static final String CLAIM_RESOURCE_ACCESS = "resource_access";

    @Override
    public Mono<AbstractAuthenticationToken> convert(Jwt source) {
        return Mono.just(extractAuthority(source)).map((authorities) -> new JwtAuthenticationToken(source, authorities));
    }

    private List<SimpleGrantedAuthority> extractAuthority(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim(CLAIM_RESOURCE_ACCESS);
        Map<String, Object> realmAccess = jwt.getClaim(CLAIM_REALM_ACCESS);
        List<String> roles = new ArrayList<>();
        if (realmAccess != null && realmAccess.containsKey("roles")) {
            roles.addAll((List<String>) realmAccess.get("roles"));
        }
        if (resourceAccess != null && resourceAccess.containsKey("ezkey-be")) {
            Map<String, List<String>> client = (Map<String, List<String>>) resourceAccess.get("ezkey-be");
            roles.addAll(client.get("roles"));
        }
        if (resourceAccess != null && resourceAccess.containsKey("ezkey-fe")) {
            Map<String, List<String>> client = (Map<String, List<String>>) resourceAccess.get("ezkey-fe");
            roles.addAll(client.get("roles"));
        }
        return roles.stream().map(SimpleGrantedAuthority::new).toList();
    }
}
