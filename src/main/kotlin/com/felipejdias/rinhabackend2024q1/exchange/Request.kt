package com.felipejdias.rinhabackend2024q1.exchange

import java.util.UUID

data class Request( val id: UUID,
                    val valor: String,
                    val tipo: PaymentType,
                    val descricao: String)
