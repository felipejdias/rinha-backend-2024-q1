package com.felipejdias.rinhabackend2024q1.exchange

data class TransactionRequest(val valor: Long,
                              val tipo: String,
                              val descricao: String)