package com.felipejdias.rinhabackend2024q1.service.impl

import com.felipejdias.rinhabackend2024q1.context.Context
import com.felipejdias.rinhabackend2024q1.db.model.Transaction
import com.felipejdias.rinhabackend2024q1.service.TransactionService
import org.springframework.stereotype.Service
import java.util.*

@Service
class DefaultTransactionService: TransactionService {
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