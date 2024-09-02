package com.felipejdias.rinhabackend2024q1.db.model

import com.felipejdias.rinhabackend2024q1.domain.PaymentType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.Instant
import java.util.*

@Entity
@Table(name = "transactions")
data class Transaction(
    @Id
    @GeneratedValue
    val id: UUID = UUID.randomUUID(),

    @Enumerated(EnumType.STRING)
    val type: PaymentType,

    @Column(nullable = false)
    val amount: Long,

    @Column(nullable = false)
    val description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    val client: Client,

    @Column(name = "created_at")
    val createdAt: Instant
)


fun Transaction.toTransacao(): com.felipejdias.rinhabackend2024q1.domain.Transaction {
    return com.felipejdias.rinhabackend2024q1.domain.Transaction(
        amount = amount,
        type = type.value,
        description = description,
        executedAt = createdAt.toString(),
    )
}

fun List<Transaction>.toTransacoes(): List<com.felipejdias.rinhabackend2024q1.domain.Transaction> {
    return this.map { it.toTransacao() }
}