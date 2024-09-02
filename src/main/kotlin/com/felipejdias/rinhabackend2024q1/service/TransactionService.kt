package com.felipejdias.rinhabackend2024q1.service

import com.felipejdias.rinhabackend2024q1.context.Context
import com.felipejdias.rinhabackend2024q1.exchange.ClientResponse
import org.springframework.stereotype.Service

@Service
interface TransactionService {

    fun create(context: Context): ClientResponse
}