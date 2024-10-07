package com.felipejdias.rinhabackend2024q1.db.model

import com.felipejdias.rinhabackend2024q1.domain.PaymentType
import com.felipejdias.rinhabackend2024q1.domain.TransactionDTO
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID

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

    @Column(name="id_client")
    val client: Long,

    @Column(name = "created_at")
    val createdAt: Instant
)


fun Transaction.toTransactionDTO(): TransactionDTO {
    return TransactionDTO(
        amount = amount,
        type = type.value,
        description = description,
        executedAt = createdAt.toString(),
    )
}

fun List<Transaction>.toTransactionsDTO(): List<TransactionDTO> {
    return this.map { it.toTransactionDTO() }
}