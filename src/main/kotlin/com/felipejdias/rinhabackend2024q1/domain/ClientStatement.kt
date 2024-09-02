package com.felipejdias.rinhabackend2024q1.domain

import com.fasterxml.jackson.annotation.JsonProperty

data class ClientStatement(
    @JsonProperty("saldo")
    var balance: Balance = Balance(),

    @JsonProperty("ultimas_transacoes")
    var recentTransactions: List<TransactionResponse> = ArrayList<TransactionResponse>()
)

// Classes Balance e TransactionResponse para contexto
data class Balance(
    @JsonProperty("total")
    var total: Long = 0,

    @JsonProperty("data_extrato")
    var statementDate: String = "",

    @JsonProperty("limite")
    var limit: Long = 0
)

data class TransactionResponse(
    @JsonProperty("valor")
    val amount: Long,

    @JsonProperty("tipo")
    val type: String,

    @JsonProperty("descricao")
    val description: String,

    @JsonProperty("realizado_em")
    val createdAt: String
){
    class Builder {
        private lateinit var type: String
        private var amount: Long = 0
        private lateinit var description: String
        private var createdAt: String = ""
        fun type(type: String) = apply { this.type = type }
        fun amount(amount: Long) = apply { this.amount = amount }
        fun description(description: String) = apply { this.description = description }
        fun createdAt(createdAt: String) = apply { this.createdAt = createdAt }

        fun build() = TransactionResponse(
            type = type,
            amount = amount,
            description = description,
            createdAt = createdAt
        )
    }
}
