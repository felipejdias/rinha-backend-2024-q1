package com.felipejdias.rinhabackend2024q1.exchange

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class TransactionRequest(
    @JsonProperty("valor")
    @Min(0)
    val amount: Long,
    @JsonProperty("tipo")
    @Pattern(regexp = "d|c")
    val type: String,
    @JsonProperty("descricao")
    @Size(min = 1, max = 10)
    val description: String)