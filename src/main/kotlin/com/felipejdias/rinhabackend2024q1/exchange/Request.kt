package com.felipejdias.rinhabackend2024q1.exchange

data class Request( val valor: String,
                    val tipo: PaymentType,
                    val descricao: String)
