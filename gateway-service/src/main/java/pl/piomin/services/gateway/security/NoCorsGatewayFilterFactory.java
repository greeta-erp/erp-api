package pl.piomin.services.gateway.security;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

public class NoCorsGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    public NoCorsGatewayFilterFactory() {
        super(Object.class);
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest().mutate()
                    .header(HttpHeaders.ORIGIN, "*")
                    .header(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "*")
                    .header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
                    .header(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*")
                    .header(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "*")
                    .build();

            return chain.filter(exchange.mutate().request(request).build());
        };
    }
}
