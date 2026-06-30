package com.learn.apigw_service.config;

import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class RoutesConfig {

    @Bean
    public RouterFunction<ServerResponse> gatewayRoutes() {
        return GatewayRouterFunctions.route("invoice_service")
                .route(RequestPredicates.path("/api/invoice/**"), HandlerFunctions.http())
                .before(BeforeFilterFunctions.uri("http://localhost:8080"))
                .build()

                .and(GatewayRouterFunctions.route("user_service")
                        .route(RequestPredicates.path("/api/user/**"), HandlerFunctions.http())
                        .before(BeforeFilterFunctions.uri("http://localhost:8081"))
                        .build());
    }
}

