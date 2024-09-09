package com.felipejdias.rinhabackend2024q1.context

import com.felipejdias.rinhabackend2024q1.exchange.TransactionRequest

class Context(
    var request: TransactionRequest,
    var clientId: Long = 0
                )