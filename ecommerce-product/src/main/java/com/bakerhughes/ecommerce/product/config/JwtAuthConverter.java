package com.bakerhughes.ecommerce.product.config;

import com.nimbusds.jwt.JWTClaimNames;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    /*
    Purpose of this Custom Converter:
     Jwt token response from Keycloak:-

     "resource_access": {
    "ecommerce-rest-api": {
      "roles": [
        "client-admin"
      ]
    },

    We need to convert this role: "client-admin" to "ROLE_client-admin" for Spring
     */

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    @Value("${jwt.auth.converter.principal-attribute}")
    private String principalAttributeName;
    @Value("${jwt.auth.converter.resource-id}")
    private String resourceId;

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities =
                Stream.concat(
                        jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                        extractResourceRoles(jwt).stream()
                        ).collect(Collectors.toSet());
        return new JwtAuthenticationToken(
                jwt, authorities, getPrincipalClaimName(jwt)
        );
    }

    private String getPrincipalClaimName(Jwt jwt) {
        String claimName = JWTClaimNames.SUBJECT;
        if(principalAttributeName != null){
            claimName = principalAttributeName;
        }
        return jwt.getClaim(claimName);
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> resourceAccess;
        Map<String, Object> resource;
        Collection<String> resourceRoles;
        if(jwt.getClaim("resource_access") == null) {
            return Set.of();
        }
        else {
            resourceAccess = jwt.getClaim("resource_access");
            if(resourceAccess.get(resourceId) == null) {
                return Set.of();
            }
            resource = (Map<String, Object>) resourceAccess.get(resourceId);
            resourceRoles = (Collection<String>) resource.get("roles");
            return resourceRoles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toSet());
        }
    }
}
