package com.felipejdias.rinhabackend2024q1.controller

import com.felipejdias.rinhabackend2024q1.context.Context
import com.felipejdias.rinhabackend2024q1.domain.ClientStatement
import com.felipejdias.rinhabackend2024q1.exchange.TransactionRequest
import com.felipejdias.rinhabackend2024q1.exchange.TransactionResponse
import com.felipejdias.rinhabackend2024q1.service.StatementService
import com.felipejdias.rinhabackend2024q1.service.TransactionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/clientes")
class ClientController( private val statementService: StatementService,
private val transactionService: TransactionService) {

    @GetMapping("/{id}/extrato")
    fun getClientStatements(@PathVariable id: Long): ClientStatement {
        return statementService.getClientStatement(clientId = id)
    }

    @PostMapping("/{clientId}/transacoes")
    fun createTransaction(
        @PathVariable("clientId") clientId: Long,
        @RequestBody transaction: TransactionRequest
    ): TransactionResponse {
        return transactionService.registerNewTransaction(Context(clientId = clientId, request = transaction))
    }

}