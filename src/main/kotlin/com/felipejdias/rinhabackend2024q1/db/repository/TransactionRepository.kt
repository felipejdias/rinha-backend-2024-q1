package com.felipejdias.rinhabackend2024q1.db.repository

import com.felipejdias.rinhabackend2024q1.db.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TransactionRepository: JpaRepository<Transaction, UUID> {

    @Query(value = """SELECT  
            SUM(CASE WHEN t.type = 'CREDITO' THEN t.amount ELSE 0 END) as totalCredit,
            SUM(CASE WHEN t.type = 'DEBITO' THEN t.amount ELSE 0 END) as totalDebit
        FROM transactions t
        WHERE t.client_id = :clientId""", nativeQuery = true)
    fun getTransactionSummariesByClientId(
        @Param("clientId") clientId: Long
    ): Map<String, Long>

    @Query("SELECT t FROM Transaction t JOIN FETCH t.client WHERE t.client.id = :clientId ORDER BY t.createdAt DESC LIMIT 10")
    fun findTop10ByClientId(clientId: Long): Optional<List<Transaction>>

}