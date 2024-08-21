package com.felipejdias.rinhabackend2024q1.exchange

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RequestPredicates.*
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration(proxyBeanMethods = false)
class Router {

    @Bean
    fun createTransactionRoute(handler: Handler): RouterFunction<ServerResponse> {
        return RouterFunctions
            .route(POST("/transaction/create/{clientId}").and(accept(MediaType.APPLICATION_JSON)), handler::transactionRequestHandler)
    }

    @Bean
    fun createClientRoute(handler: Handler): RouterFunction<ServerResponse> {
        return RouterFunctions
            .route(POST("/client/create").and(accept(MediaType.APPLICATION_JSON)), handler::clientRequestHandler)
    }

    @Bean
    fun getClientRoute(handler: Handler): RouterFunction<ServerResponse> {
        return RouterFunctions
            .route(GET("/client").and(accept(MediaType.APPLICATION_JSON)), handler::getClientHandler)
    }

    @Bean
    fun getStatementRoute(handler: Handler): RouterFunction<ServerResponse> {
        return RouterFunctions
            .route(GET("/statement/{clientId}").and(accept(MediaType.APPLICATION_JSON)), handler::getStatementHandler)
    }
}