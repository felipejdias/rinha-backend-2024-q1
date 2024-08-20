package com.felipejdias.rinhabackend2024q1.domain

import java.time.Instant
import java.util.*

data class ClientStatement(
    val balance: Balance,
    val lastTransactions: List<TransactionDomain>

    )

data class Balance(
    val total: Long,
    val date: Instant,
    val limit: Long,
    )
