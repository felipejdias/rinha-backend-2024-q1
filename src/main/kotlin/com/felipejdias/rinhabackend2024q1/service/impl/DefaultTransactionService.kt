package com.felipejdias.rinhabackend2024q1.service.impl

import com.felipejdias.rinhabackend2024q1.context.Context
import com.felipejdias.rinhabackend2024q1.db.model.Client
import com.felipejdias.rinhabackend2024q1.db.model.Transaction
import com.felipejdias.rinhabackend2024q1.db.repository.ClientRepository
import com.felipejdias.rinhabackend2024q1.db.repository.TransactionRepository
import com.felipejdias.rinhabackend2024q1.domain.PaymentType
import com.felipejdias.rinhabackend2024q1.exception.ClientNotFoundException
import com.felipejdias.rinhabackend2024q1.exception.InvalidParameterException
import com.felipejdias.rinhabackend2024q1.exchange.TransactionRequest
import com.felipejdias.rinhabackend2024q1.exchange.TransactionResponse
import com.felipejdias.rinhabackend2024q1.service.TransactionService
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class DefaultTransactionService(
    private val clientRepository: ClientRepository,
    private val transactionRepository: TransactionRepository
): TransactionService {

    private val logger = LoggerFactory.getLogger(DefaultTransactionService::class.java)

    @Transactional
    override fun registerNewTransaction(context: Context): TransactionResponse {

        val client = clientRepository.findClientAndLock(context.clientId).orElseThrow{
            logger.error("Cliente não encontrado para o ID: {}", context.clientId)
            ClientNotFoundException()
        }

        logger.debug("Cliente encontrado: {}", client)

        val transaction = createTransaction(client, context.request)
        logger.debug("Transação criada: {}", transaction)

        registerNewClientBalance(client, transaction)
        logger.debug("Saldo do cliente atualizado: {}", client.balance)

        transactionRepository.save(transaction)
        logger.info("Transação registrada com sucesso para o cliente ID: {}", context.clientId)

        return TransactionResponse(limite = client.limit, saldo = client.balance)
    }

    private fun createTransaction(client: Client, transaction: TransactionRequest): Transaction {
        logger.debug("Criando transação para o cliente: {}", client.id)

        return Transaction(
            type = PaymentType.entries.find { it.value == transaction.type }!!,
            amount = transaction.amount!!.toLong(),
            description = transaction.description!!,
            client = client,
            createdAt = Instant.now()
        ).also {
            logger.debug("Transação criada: {}", it)
        }
    }

    private fun registerNewClientBalance(client: Client, transaction: Transaction) {
        logger.debug("Atualizando saldo do cliente: {}", client.id)

        return if (transaction.type.value.equals("c", ignoreCase = true)) {
            client.credit(transaction.amount)
            logger.debug("Saldo creditado: {}", client.balance)
        } else if (transaction.type.value.equals("d", ignoreCase = true)) {
            client.debit(transaction.amount)
            logger.debug("Saldo debitado: {}", client.balance)
        } else {
            logger.error("Tipo de transação inválido: {}", transaction.type.value)
            throw InvalidParameterException()
        }
    }
}
