package com.felipejdias.rinhabackend2024q1.context

import com.felipejdias.rinhabackend2024q1.exchange.TransactionRequest
import java.time.Instant

class Context(
    var request: TransactionRequest,
    var createAt: Instant = Instant.now(),
    var clientId: Long = 0
                )    {
}