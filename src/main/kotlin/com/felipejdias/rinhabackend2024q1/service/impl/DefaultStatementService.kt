package com.felipejdias.rinhabackend2024q1.service.impl

import com.felipejdias.rinhabackend2024q1.db.model.toTransactionsDTO
import com.felipejdias.rinhabackend2024q1.db.repository.ClientRepository
import com.felipejdias.rinhabackend2024q1.db.repository.TransactionRepository
import com.felipejdias.rinhabackend2024q1.domain.Balance
import com.felipejdias.rinhabackend2024q1.domain.ClientStatement
import com.felipejdias.rinhabackend2024q1.exception.ClientNotFoundException
import com.felipejdias.rinhabackend2024q1.service.StatementService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class DefaultStatementService(private val transactionRepository: TransactionRepository,
    private val clientRepository: ClientRepository): StatementService {

    override fun getClientStatement(clientId: Long): ClientStatement {
        val client = clientRepository.findById(clientId).orElseThrow{
            ClientNotFoundException()
        }
        val transactions = transactionRepository.findTop10ByClientOrderByCreatedAtDesc(client.id)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val formattedDateTime = formatter.format( Instant.now().atZone(ZoneId.systemDefault()))
        val saldo = Balance(total = client.balance, statementDate = formattedDateTime, limit = client.limit)
        return ClientStatement(balance = saldo,  lastTransactionDTOS = transactions.toTransactionsDTO())

    }
}