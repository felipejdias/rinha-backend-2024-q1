package com.felipejdias.rinhabackend2024q1.controller

import com.felipejdias.rinhabackend2024q1.context.Context
import com.felipejdias.rinhabackend2024q1.db.model.Client
import com.felipejdias.rinhabackend2024q1.domain.ExtratoBancario
import com.felipejdias.rinhabackend2024q1.exception.InvalidParameterException
import com.felipejdias.rinhabackend2024q1.exchange.TransactionRequest
import com.felipejdias.rinhabackend2024q1.exchange.TransactionResponse
import com.felipejdias.rinhabackend2024q1.service.ClientService
import com.felipejdias.rinhabackend2024q1.service.StatementService
import com.felipejdias.rinhabackend2024q1.service.TransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/clientes")
class ClientController {

    @Autowired
    private lateinit var clientService: ClientService

    @Autowired
    private lateinit var statementService: StatementService

    @Autowired
    private lateinit var transactionService: TransactionService


    @GetMapping("/{id}")
    fun getClientById(@PathVariable id: Long) : Client {
        return clientService.findById(id)
    }

    @PostMapping
    fun createClient(@RequestBody client: Client): Client {
       return clientService.createOrUpdateClient(client)
    //TODO nao funciona ainda pois já foi pré
    // inserido na base de dados  alguns registros precisa bolar um jeito do
    // primeiro a ser inserido já começar no 7 pois na doc oficial fala q o 6 especificamente nao deve ser utilizado

    }

    @GetMapping("/{id}/extrato")
    fun getClientStatements(@PathVariable id: Long): ExtratoBancario {
        return statementService.getClientStatement(clientId = id)
    }

    @PostMapping("/{clientId}/transacoes")
    fun createTransaction(
        @PathVariable("clientId") clientId: Long,
        @RequestBody transaction: TransactionRequest
    ): TransactionResponse {
        validateTransactionRequest(transaction)
        return transactionService.create(Context(clientId = clientId, request = transaction)).response!!
    }

    private fun validateTransactionRequest(transaction: TransactionRequest){
        if (transaction.tipo != "c" && transaction.tipo != "d") throw InvalidParameterException()
        if (transaction.descricao.length !in 1..10) throw InvalidParameterException()
    }
}