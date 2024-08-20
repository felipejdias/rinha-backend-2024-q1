package com.felipejdias.rinhabackend2024q1.exchange

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.util.*

@Component
class Handler {
    fun requestHandler(request: ServerRequest): Mono<ServerResponse> {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(Request(
                id = UUID.randomUUID(),
                valor = "100",
                tipo = PaymentType.CREDITO,
                descricao = "1234"
            )))
    }
}