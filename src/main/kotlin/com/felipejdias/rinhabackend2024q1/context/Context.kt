package com.felipejdias.rinhabackend2024q1.context

import com.felipejdias.rinhabackend2024q1.db.model.TransactionEntity
import com.felipejdias.rinhabackend2024q1.exchange.TransactionRequest
import java.time.Instant

class Context(
    var request: TransactionRequest,
    var clientId: Long = 0
                )

    fun Context.requestToEntity(clientId: Long): TransactionEntity =
        TransactionEntity(
             type = this.request.tipo,
             amount = this.request.valor,
             description = this.request.descricao,
             clientId = clientId,
             createdAt = Instant.now().toString())