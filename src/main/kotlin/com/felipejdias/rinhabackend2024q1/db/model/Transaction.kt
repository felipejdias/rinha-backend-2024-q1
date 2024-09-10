package com.felipejdias.rinhabackend2024q1.db.model

import com.felipejdias.rinhabackend2024q1.domain.PaymentType
import com.felipejdias.rinhabackend2024q1.domain.TransactionDTO
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import java.math.BigInteger
import java.time.Instant

@Entity
@Table(name = "transactions")
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq_generator")
    @SequenceGenerator(name = "transaction_seq_generator", sequenceName = "transaction_id_sequence")
    val id: BigInteger? = null,

    @Enumerated(EnumType.STRING)
    val type: PaymentType,

    @Column(nullable = false)
    val amount: Long,

    @Column(nullable = false)
    val description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idt_client", nullable = false)
    val client: Client,

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