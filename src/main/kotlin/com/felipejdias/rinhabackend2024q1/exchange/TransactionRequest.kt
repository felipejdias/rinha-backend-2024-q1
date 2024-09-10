package com.felipejdias.rinhabackend2024q1.exchange

import com.fasterxml.jackson.annotation.JsonProperty


data class TransactionRequest(
    @get:JsonProperty("valor")
    val amount: Double?,
    @get:JsonProperty("tipo")
    val type: String?,
    @field:JsonProperty("descricao")
    val description: String?
)