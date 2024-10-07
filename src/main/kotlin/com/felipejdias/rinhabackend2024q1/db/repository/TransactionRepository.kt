package com.felipejdias.rinhabackend2024q1.db.repository

import com.felipejdias.rinhabackend2024q1.db.model.Client
import com.felipejdias.rinhabackend2024q1.db.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TransactionRepository: JpaRepository<Transaction, UUID> {

    fun findTop10ByClientOrderByCreatedAtDesc(client: Long): List<Transaction>

}