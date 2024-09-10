package com.felipejdias.rinhabackend2024q1.controller

import com.felipejdias.rinhabackend2024q1.context.Context
import com.felipejdias.rinhabackend2024q1.domain.ClientStatement
import com.felipejdias.rinhabackend2024q1.exception.RequestValidationExceptions
import com.felipejdias.rinhabackend2024q1.exchange.TransactionRequest
import com.felipejdias.rinhabackend2024q1.exchange.TransactionResponse
import com.felipejdias.rinhabackend2024q1.service.StatementService
import com.felipejdias.rinhabackend2024q1.service.TransactionService

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
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

    private val logger = LoggerFactory.getLogger(ClientController::class.java)

    @GetMapping("/{id}/extrato")
    fun getClientStatements(@PathVariable id: Long): ResponseEntity<ClientStatement> {
        logger.info("Recebendo requisição para criar cliente: {}", id)

        val clientResponse = statementService.getClientStatement(id)

        logger.info("Cliente: {} - Extrato: {}", id, clientResponse)

        return ResponseEntity.ok(clientResponse)
    }

    @PostMapping("/{clientId}/transacoes")
    fun createTransaction(
        @PathVariable("clientId") clientId: Long,
        @RequestBody transaction: TransactionRequest,
        result: BindingResult
    ): ResponseEntity<TransactionResponse> {

        validateTransaction(transaction)

        logger.info("Recebendo requisição para obter cliente com ID: {}", clientId)

        val transactionResponse = transactionService.registerNewTransaction(Context(clientId = clientId, request = transaction))

        logger.info("Cliente: {} - Transação: {}", clientId, transactionResponse)

        return ResponseEntity.ok(transactionResponse)
    }

    private fun validateTransaction(transaction: TransactionRequest) {
        if (transaction.amount == null) { throw RequestValidationExceptions("amount must not be null", field="amount") }
        if (transaction.description == null) { throw RequestValidationExceptions("description must not be null", field="description") }
        if (transaction.type == null) { throw RequestValidationExceptions("type must not be null",  field="type") }
        if (transaction.amount <= 0)  { throw RequestValidationExceptions("amount must be greater than zero", field="amount") }
        if (transaction.description.isEmpty() || transaction.description.length > 10 ) { throw RequestValidationExceptions("description must be between 1 and 10 characters", field = "description") }
        if (!transaction.type.matches(Regex("^[cd]$"))) { throw  RequestValidationExceptions("type must be 'c' or 'd'", field="type") }
        if ((transaction.amount % 1) != 0.0) { throw RequestValidationExceptions("amount must be an integer", field="amount") }
    }

}