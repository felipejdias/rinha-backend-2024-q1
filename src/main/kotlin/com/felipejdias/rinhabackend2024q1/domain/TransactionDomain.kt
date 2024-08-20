package com.felipejdias.rinhabackend2024q1.domain

import java.time.Instant
import java.util.*

data class TransactionDomain(
    val id: UUID,
    val type: PaymentType,
    val amount: Long,
    val description: String,
    val createdAt: Instant
){
    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other?.equals(this.id)!!
    }

    override fun toString(): String {
        return "Transaction(id=$id, type=$type, amount=$amount, description='$description', createdAt=$createdAt)"
    }
}
