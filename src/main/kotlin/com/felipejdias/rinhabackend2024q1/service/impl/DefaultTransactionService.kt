package com.felipejdias.rinhabackend2024q1.service.impl

import com.felipejdias.rinhabackend2024q1.context.Context
import com.felipejdias.rinhabackend2024q1.context.requestToEntity
import com.felipejdias.rinhabackend2024q1.db.model.Transaction
import com.felipejdias.rinhabackend2024q1.db.repository.TransactionRepository
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


    override fun create(context: Context): Transaction {
        val client = clientService.findById(context.clientId).orElseThrow { RuntimeException("client not found") }
        val transaction = context.requestToEntity(client = client, createdAt =  Instant.now())
        return transaction
    }

    override fun search(id: UUID): Optional<Transaction> {
      return repository.findById(id)
    }

    override fun getAllTransactionsByClient(id: Long):  Optional<List<Transaction>> {
        TODO("Not yet implemented")
    }
}