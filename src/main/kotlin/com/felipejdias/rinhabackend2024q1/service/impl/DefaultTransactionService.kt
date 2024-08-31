package com.felipejdias.rinhabackend2024q1.service.impl

import com.felipejdias.rinhabackend2024q1.context.Context
import com.felipejdias.rinhabackend2024q1.context.requestToEntity
import com.felipejdias.rinhabackend2024q1.db.model.Client
import com.felipejdias.rinhabackend2024q1.db.model.Transaction
import com.felipejdias.rinhabackend2024q1.db.repository.TransactionRepository
import com.felipejdias.rinhabackend2024q1.domain.PaymentType
import com.felipejdias.rinhabackend2024q1.exchange.TransactionResponse
import com.felipejdias.rinhabackend2024q1.service.ClientService
import com.felipejdias.rinhabackend2024q1.service.TransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import java.time.Instant
import java.util.*

@Service
class DefaultTransactionService: TransactionService {

    @Autowired
    private lateinit var clientService: ClientService

    @Autowired
    private lateinit var repository: TransactionRepository


    override fun create(context: Context): Context {
        val client = clientService.findById(context.clientId)
            .orElseThrow { HttpClientErrorException(NOT_FOUND, "ClientId not found") }  //TODO aqui esse erro precisa ser respondido no body e o status code

        val transaction = context.requestToEntity(client = client)
        val clientUpdated = registerNewTransaction(client, transaction)

        val response = TransactionResponse(limite = clientUpdated.limit, saldo = clientUpdated.balance)

        return Context(request = context.request, clientId = context.clientId, response =  response)
    }

    override fun search(id: UUID): Optional<Transaction> {
      return repository.findById(id)
    }

    override fun getAllTransactionsByClient(id: Long):  Optional<List<Transaction>> {
       return repository.findTop10ByClientId(id)
    }

    private fun registerNewTransaction(client: Client, transaction: Transaction):Client {
        val actualBalance = calculateNewClientBalance(client)
        val clientLimit = 0 - client.limit
        if (transaction.type == PaymentType.DEBITO && actualBalance.minus(transaction.amount) < clientLimit ) {
            throw  HttpClientErrorException(UNPROCESSABLE_ENTITY, "Client limit exceeded") //TODO aqui esse erro precisa ser respondido no body e o status code
        }else if(transaction.type == PaymentType.DEBITO){
            client.balance = actualBalance.minus(transaction.amount)
        }else if(transaction.type == PaymentType.CREDITO){
            client.balance = actualBalance.plus(transaction.amount)
        }

        repository.saveAndFlush(transaction.copy(createdAt = Instant.now()))
        return clientService.createOrUpdateClient(client)
    }

    override fun calculateNewClientBalance(client: Client): Long{
        val totalDebit = repository.getSumTotalTransactionAmountByType(PaymentType.DEBITO.name, clientId = client.id).orElse(0)
        val totalCredit =  repository.getSumTotalTransactionAmountByType(PaymentType.CREDITO.name, clientId = client.id).orElse(0)
        return totalCredit - totalDebit

    }
}