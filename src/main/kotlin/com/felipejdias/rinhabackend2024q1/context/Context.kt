package com.felipejdias.rinhabackend2024q1.context

import com.felipejdias.rinhabackend2024q1.db.model.Client
import com.felipejdias.rinhabackend2024q1.db.model.Transaction
import com.felipejdias.rinhabackend2024q1.domain.PaymentType
import com.felipejdias.rinhabackend2024q1.exchange.TransactionRequest
import com.felipejdias.rinhabackend2024q1.exchange.TransactionResponse
import java.time.Instant

class Context(
    var request: TransactionRequest,
    var clientId: Long = 0,
    var response: TransactionResponse? = null,
                )

    fun Context.requestToEntity(client: Client): Transaction =
         Transaction(
             type = PaymentType.values().find { it.value == request.type }!!,
             amount = this.request.amount,
             description = this.request.description,
             client = client,
             createdAt = Instant.now())