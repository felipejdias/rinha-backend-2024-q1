package com.felipejdias.rinhabackend2024q1.controller

import com.felipejdias.rinhabackend2024q1.context.Context
import com.felipejdias.rinhabackend2024q1.domain.ClientStatement
import com.felipejdias.rinhabackend2024q1.exception.ClientLimitExceededException
import com.felipejdias.rinhabackend2024q1.exception.ClientNotFoundException
import com.felipejdias.rinhabackend2024q1.exception.InvalidParameterException
import com.felipejdias.rinhabackend2024q1.exchange.TransactionRequest
import com.felipejdias.rinhabackend2024q1.exchange.TransactionResponse
import com.felipejdias.rinhabackend2024q1.service.StatementService
import com.felipejdias.rinhabackend2024q1.service.TransactionService
import org.postgresql.util.PSQLException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.SQLException

@RestController
@RequestMapping("/clientes")
class ClientController {

    @Autowired
    private lateinit var statementService: StatementService

    @Autowired
    private lateinit var transactionService: TransactionService


    @GetMapping("/{id}/extrato")
    fun getClientStatements(@PathVariable id: Long): ClientStatement {
        return try {
            statementService.getClientStatement(clientId = id)
        } catch (ex: SQLException) {
            throw ClientNotFoundException()
        }
    }

    @PostMapping("/{clientId}/transacoes")
    fun createTransaction(
        @PathVariable("clientId") clientId: Long,
        @RequestBody transaction: TransactionRequest
    ): TransactionResponse {
        validateTransactionRequest(transaction)
        return try {
            transactionService.create(Context(clientId = clientId, request = transaction))
        } catch (ex: SQLException) {
            throw ClientLimitExceededException()
        } catch (ex: PSQLException){
            throw ex
        }
    }

    private fun validateTransactionRequest(transaction: TransactionRequest){
        if (transaction.tipo != "c" && transaction.tipo != "d") throw InvalidParameterException()
        if (transaction.descricao.length !in 1..10) throw InvalidParameterException()
    }
}