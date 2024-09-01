package com.felipejdias.rinhabackend2024q1.service.impl

import com.felipejdias.rinhabackend2024q1.context.Context
import com.felipejdias.rinhabackend2024q1.context.requestToEntity
import com.felipejdias.rinhabackend2024q1.db.repository.TransactionRepository
import com.felipejdias.rinhabackend2024q1.exchange.TransactionResponse
import com.felipejdias.rinhabackend2024q1.service.TransactionService
import org.springframework.stereotype.Service

@Service
class DefaultTransactionService: TransactionService {

    override fun create(context: Context): TransactionResponse {
        val transaction = context.requestToEntity(context.clientId)
        val transactionRepository = TransactionRepository()
        return transactionRepository.registerTransaction(context.clientId, transaction)
    }
}