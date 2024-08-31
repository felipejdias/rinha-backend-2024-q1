package com.felipejdias.rinhabackend2024q1.exchange

import com.fasterxml.jackson.annotation.JsonProperty

data class TransactionRequest(
    @JsonProperty("valor")
    val valor: Long,
    @JsonProperty("tipo")
    val tipo: String,
    @JsonProperty("descricao")
    val descricao: String)