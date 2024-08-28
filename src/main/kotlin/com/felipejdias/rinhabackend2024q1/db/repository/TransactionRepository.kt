package com.felipejdias.rinhabackend2024q1.db.repository

import com.felipejdias.rinhabackend2024q1.db.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TransactionRepository: JpaRepository<Transaction, UUID> {

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.type = CAST(:type AS STRING) AND t.client.id = CAST(:clientId AS INTEGER)")
    fun getSumTotalTransactionAmountByType(
        @Param("type") type: String,
        @Param("clientId") clientId: Long
    ): Optional<Long>

}