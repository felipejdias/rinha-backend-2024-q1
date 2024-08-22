package com.felipejdias.rinhabackend2024q1.service.impl

import com.felipejdias.rinhabackend2024q1.context.Context
import com.felipejdias.rinhabackend2024q1.context.requestToEntity
import com.felipejdias.rinhabackend2024q1.db.model.Transaction
import com.felipejdias.rinhabackend2024q1.db.repository.TransactionRepository
import com.felipejdias.rinhabackend2024q1.service.ClientService
import com.felipejdias.rinhabackend2024q1.service.TransactionService
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class DefaultTransactionService: TransactionService {
    private lateinit var clientService: ClientService
    private lateinit var repository: TransactionRepository

    override fun create(context: Context): Transaction {
        val client = clientService.findById(context.clientId).orElseThrow { RuntimeException("client not found") }
        //TODO trocar para chamar uma fila rabbitmq
        return repository.save( context.requestToEntity(client = client, createdAt =  Instant.now()))
    }

    override fun search(id: UUID): Transaction {
        TODO("Not yet implemented")
    }

    override fun getAllTransactionsByClient(id: Long): List<Transaction> {
        TODO("Not yet implemented")
    }
}