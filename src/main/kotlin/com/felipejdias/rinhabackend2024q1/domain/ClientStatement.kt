package com.felipejdias.rinhabackend2024q1.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class ClientStatement(
    @JsonProperty("saldo")
    val balance: Balance,

    @JsonProperty("ultimas_transacoes")
    val lastTransactionDTOS: List<TransactionDTO>
)

data class Balance(
    @JsonProperty("total")
    val total: Long,

    @JsonProperty("data_extrato")
    val statementDate: String,

    @JsonProperty("limite")
    val limit: Long
)

data class TransactionDTO(
    @JsonProperty("valor")
    val amount: Long,

    @JsonProperty("tipo")
    val type: String,

    @JsonProperty("descricao")
    val description: String,

    @JsonProperty("realizado_em")
    val executedAt: String
)
