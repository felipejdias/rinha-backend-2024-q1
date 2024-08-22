package com.felipejdias.rinhabackend2024q1.context

import com.felipejdias.rinhabackend2024q1.db.model.Client
import com.felipejdias.rinhabackend2024q1.db.model.Transaction
import com.felipejdias.rinhabackend2024q1.exchange.TransactionRequest
import java.time.Instant

class Context(
    var request: TransactionRequest,
    var clientId: Long = 0
                )

    fun Context.requestToEntity( createdAt: Instant, client: Client): Transaction =
         Transaction(
             id = this.request.id,
             type = this.request.type,
             amount = this.request.amount,
             description = this.request.description,
             createdAt = createdAt,
             client = client)