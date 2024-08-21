package com.felipejdias.rinhabackend2024q1.exchange

import com.felipejdias.rinhabackend2024q1.domain.PaymentType
import java.util.UUID

data class TransactionRequest(val id: UUID = UUID.randomUUID(),
                              val amount: Long,
                              val type: PaymentType,
                              val description: String)