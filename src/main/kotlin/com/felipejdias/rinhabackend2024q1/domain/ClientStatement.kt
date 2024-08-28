package com.felipejdias.rinhabackend2024q1.domain

data class ExtratoBancario(
    val saldo: Saldo,
    val ultimasTransacoes: List<Transacao>
)

data class Saldo(
    val total: Int,
    val dataExtrato: String,
    val limite: Int
)

data class Transacao(
    val valor: Int,
    val tipo: String,
    val descricao: String,
    val realizadaEm: String
)
