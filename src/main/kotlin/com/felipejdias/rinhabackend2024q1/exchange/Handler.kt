package com.felipejdias.rinhabackend2024q1.exchange

import com.felipejdias.rinhabackend2024q1.context.Context
import com.felipejdias.rinhabackend2024q1.service.TransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class Handler {

    @Autowired
    lateinit var transactionService: TransactionService

    fun requestHandler(serverRequest: ServerRequest): Mono<ServerResponse> {
        return  serverRequest.bodyToMono(Request::class.java)
            .flatMap {  ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(transactionService.create(Context(
                    request = it,
                    clientId = serverRequest.pathVariable("id").toLong()
                )))) }
            .onErrorResume { ServerResponse.badRequest().build() }

    }
}