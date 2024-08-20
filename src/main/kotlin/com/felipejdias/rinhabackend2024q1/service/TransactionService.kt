package com.felipejdias.rinhabackend2024q1.service

import com.felipejdias.rinhabackend2024q1.context.Context
import com.felipejdias.rinhabackend2024q1.model.Transaction
import org.springframework.stereotype.Service
import java.util.*

@Service
interface TransactionService {

    fun create(context: Context): Transaction

    fun search(id: UUID): Transaction

    fun getAllTransactionsByClient(id: Long): List<Transaction>
}