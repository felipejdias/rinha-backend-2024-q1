package com.felipejdias.rinhabackend2024q1.service.impl

import com.felipejdias.rinhabackend2024q1.context.Context
import com.felipejdias.rinhabackend2024q1.context.requestToEntity
import com.felipejdias.rinhabackend2024q1.db.model.Client
import com.felipejdias.rinhabackend2024q1.db.model.Transaction
import com.felipejdias.rinhabackend2024q1.db.repository.TransactionRepository
import com.felipejdias.rinhabackend2024q1.domain.PaymentType
import com.felipejdias.rinhabackend2024q1.exception.ClientLimitExceededException
import com.felipejdias.rinhabackend2024q1.exchange.TransactionResponse
import com.felipejdias.rinhabackend2024q1.service.ClientService
import com.felipejdias.rinhabackend2024q1.service.TransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
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
            throw  ClientLimitExceededException()
        }else if(transaction.type == PaymentType.DEBITO){
            client.balance = actualBalance.minus(transaction.amount)
        }else if(transaction.type == PaymentType.CREDITO){
            client.balance = actualBalance.plus(transaction.amount)
        }

        repository.saveAndFlush(transaction.copy(createdAt = Instant.now()))
        return clientService.createOrUpdateClient(client)
    }

    override fun calculateNewClientBalance(client: Client): Long{
        val transactionSummary  = repository.getTransactionSummariesByClientId( clientId = client.id)
        val totalCredit = transactionSummary.filterKeys { it == "totalCredit" }.values.filterNotNull().sum()
        val totalDebit = transactionSummary.filterKeys { it == "totalDebit" }.values.filterNotNull().sum()
        return totalCredit - totalDebit

    }
}