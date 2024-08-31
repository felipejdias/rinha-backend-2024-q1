package com.felipejdias.rinhabackend2024q1.domain

//TODO escrevi em portugues mas talvez o ideal seja
// em ingles pra manter o padrão do projeto
//TODO com a classe em ingles precisaria bolar um jeito de depois converter pra portugues
// pois da forma que está ficará de acordo com o response descrito na rinha https://github.com/zanfranceschi/rinha-de-backend-2024-q1
data class ExtratoBancario(
    val saldo: Saldo,
    val ultimas_transacoes: List<Transacao>
)

data class Saldo(
    val total: Long,
    val data_extrato: String,
    val limite: Long
)

data class Transacao(
    val valor: Long,
    val tipo: String,
    val descricao: String,
    val realizado_em: String
)
