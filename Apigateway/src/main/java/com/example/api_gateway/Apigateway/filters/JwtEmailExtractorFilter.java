package com.example.api_gateway.Apigateway.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class JwtEmailExtractorFilter implements GlobalFilter, Ordered {

    // HEX-encoded secret key
    private final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    // Properly decode HEX-encoded key to get the signing key
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("JwtEmailExtractorFilter invoked");

        HttpHeaders headers = exchange.getRequest().getHeaders();
        String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                // Parse and verify the JWT signature
                Claims claims = Jwts.parser()
                        .setSigningKey(getSignInKey())
                        .parseClaimsJws(token)
                        .getBody();

                String email = claims.getSubject();
                System.out.println("Extracted email: " + email);

                // If email is null or empty, reject
                if (email == null || email.isEmpty()) {
                    return unauthorizedResponse(exchange, "Invalid token or email, please login again");
                }

                // Add email to headers
                ServerWebExchange mutatedExchange = exchange.mutate()
                        .request(builder -> builder.header("X-User-Email", email))
                        .build();

                return chain.filter(mutatedExchange);

            } catch (Exception e) {
                System.out.println("JWT parsing/verification failed: " + e.getMessage());
                return unauthorizedResponse(exchange, "Invalid token or email, please login again");
            }
        }

        // If no Authorization header, continue (or reject if you want to enforce JWT for all requests)
        return chain.filter(exchange);
    }

    // Send custom unauthorized response
    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String responseBody = "{ \"error\": \"" + message + "\" }";
        byte[] bytes = responseBody.getBytes(StandardCharsets.UTF_8);

        return exchange.getResponse()
                .writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
    }

    @Override
    public int getOrder() {
        return -1; // High precedence
    }
}
