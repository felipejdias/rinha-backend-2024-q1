package com.felipejdias.rinhabackend2024q1.repository

import com.felipejdias.rinhabackend2024q1.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TransactionRepository: JpaRepository<Transaction, UUID> {
}