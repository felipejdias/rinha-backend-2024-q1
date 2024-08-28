package com.felipejdias.rinhabackend2024q1.domain

//TODO escrevi em portugues mas talvez o ideal seja
// em ingles pra manter o padrão do projeto
//TODO com a classe em ingles precisaria bolar um jeito de depois converter pra portugues
// pois da forma que está ficará de acordo com o response descrito na rinha https://github.com/zanfranceschi/rinha-de-backend-2024-q1
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
