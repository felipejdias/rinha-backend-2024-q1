package com.felipejdias.rinhabackend2024q1.domain

data class TransactionSummary(
    @Transient
    val totalCredit: Long,
    @Transient
    val totalDebit: Long)