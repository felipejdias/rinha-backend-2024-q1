package com.felipejdias.rinhabackend2024q1.service.impl

import com.felipejdias.rinhabackend2024q1.db.repository.TransactionRepository
import com.felipejdias.rinhabackend2024q1.domain.ClientStatement
import com.felipejdias.rinhabackend2024q1.service.StatementService

import org.springframework.stereotype.Service

@Service
class DefaultStatementService: StatementService {

    private val transactionRepository = TransactionRepository()

    override fun getClientStatement(clientId: Long): ClientStatement {
        return transactionRepository.calculateClientStatement( clientId)
    }


}