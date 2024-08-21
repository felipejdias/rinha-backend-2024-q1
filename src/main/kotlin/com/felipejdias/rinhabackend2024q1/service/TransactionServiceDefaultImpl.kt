package com.felipejdias.rinhabackend2024q1.service

import com.felipejdias.rinhabackend2024q1.context.Context
import com.felipejdias.rinhabackend2024q1.model.Transaction
import org.springframework.stereotype.Service
import java.util.*

@Service
class TransactionServiceDefaultImpl: TransactionService {
    override fun create(context: Context): Transaction {
        TODO("Not yet implemented")
    }

    override fun search(id: UUID): Transaction {
        TODO("Not yet implemented")
    }

    override fun getAllTransactionsByClient(id: Long): List<Transaction> {
        TODO("Not yet implemented")
    }
}