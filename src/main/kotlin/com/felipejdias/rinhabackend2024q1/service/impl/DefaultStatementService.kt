package com.felipejdias.rinhabackend2024q1.service.impl

import com.felipejdias.rinhabackend2024q1.db.model.toTransacoes
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

    private val logger = LoggerFactory.getLogger(DefaultStatementService::class.java)

    override fun getClientStatement(clientId: Long): ClientStatement {

        val client = clientRepository.findById(clientId).orElseThrow{
            logger.error("Cliente não encontrado para o ID: {}", clientId)
            ClientNotFoundException()
        }

        logger.debug("Cliente encontrado: {}", client)

        val transactions = transactionRepository.findTop10ByClientOrderByCreatedAtDesc(client)
        logger.debug("Transações encontradas: {}", transactions)

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val formattedDateTime = formatter.format( Instant.now().atZone(ZoneId.systemDefault()))
        val saldo = Balance(total = client.balance, statementDate = formattedDateTime, limit = client.limit)

        logger.info("Extrato gerado com sucesso para o cliente ID: {}", client)

        return ClientStatement(balance = saldo,  lastTransactions = transactions.toTransacoes())

    }
}