package com.felipejdias.rinhabackend2024q1.db.model

import com.felipejdias.rinhabackend2024q1.domain.PaymentType
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.Instant
import java.util.UUID

@Entity
data class Transaction(
    @Id
    val id: UUID,
    val type: PaymentType,
    val amount: Long,
    val description: String,
    val createdAt: Instant )