package com.mcart.productgatewsvc.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class JwtHeaderEnrichmentFilter implements GlobalFilter, Ordered {

    private static final String INTERNAL_HEADER = "authoeized-user-id";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        /*return exchange.getPrincipal()
                .filter(JwtAuthenticationToken.class::isInstance)
                .cast(JwtAuthenticationToken.class)
                .map(jwtAuth -> {
                    String userId = jwtAuth.getToken().getSubject();
                    return exchange.mutate()
                            .request(r -> r.header(INTERNAL_HEADER, userId))
                            .build();
                })
                .defaultIfEmpty(exchange)
                .flatMap(chain::filter);
        */
    	return exchange.getPrincipal()
                .filter(JwtAuthenticationToken.class::isInstance)
                .cast(JwtAuthenticationToken.class)
                .map(jwtAuth -> {
                    String userId = jwtAuth.getToken().getSubject();

                    /*ServerHttpRequest mutatedRequest = exchange.getRequest()
                            .mutate()
                            .header(INTERNAL_HEADER, userId)
                            .build();
					*/
                    return exchange.mutate()
                            .request(r -> r.headers(httpHeaders -> {
                            	httpHeaders.set(INTERNAL_HEADER, userId);
                            })).build();
                })
                .defaultIfEmpty(exchange)
                .flatMap(chain::filter);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
