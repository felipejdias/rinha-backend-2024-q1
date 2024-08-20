package com.felipejdias.rinhabackend2024q1.exchange

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RequestPredicates.POST
import org.springframework.web.reactive.function.server.RequestPredicates.accept
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration(proxyBeanMethods = false)
class Router {

    @Bean
    fun route(handler: Handler): RouterFunction<ServerResponse> {
        return RouterFunctions
            .route(POST("/create/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::requestHandler)
    }
}