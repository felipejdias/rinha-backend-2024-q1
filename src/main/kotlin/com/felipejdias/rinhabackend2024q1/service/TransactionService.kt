package com.felipejdias.rinhabackend2024q1.service

import com.felipejdias.rinhabackend2024q1.context.Context
import com.felipejdias.rinhabackend2024q1.exchange.TransactionResponse
import org.springframework.stereotype.Service

@Service
interface TransactionService {

    fun registerNewTransaction(context: Context): TransactionResponse

}