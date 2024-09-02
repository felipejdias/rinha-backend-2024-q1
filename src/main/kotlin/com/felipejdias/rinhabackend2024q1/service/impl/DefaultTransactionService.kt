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
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class DefaultTransactionService(
    private val clientRepository: ClientRepository,
    private val transactionRepository: TransactionRepository
): TransactionService {



    @Transactional
    override fun registerNewTransaction(context: Context): TransactionResponse {
        val client = clientRepository.findClientAndLock(context.clientId).orElseThrow{ ClientNotFoundException()}
        val transaction = createTransaction(client, context.request)
        registerNewClientBalance(client, transaction)
        transactionRepository.save(transaction)
        return TransactionResponse(limite = client.limit, saldo = client.balance)
    }

    private fun createTransaction(client: Client, transaction: TransactionRequest): Transaction {
       return Transaction(
            type = PaymentType.entries.find { it.value == transaction.type }!!,
            amount = transaction.amount,
            description = transaction.description,
            client = client,
            createdAt = Instant.now())

    }

    private fun registerNewClientBalance(client: Client, transaction: Transaction) {
        return if (transaction.type.value.equals("c", ignoreCase = true)) {
            client.credit(transaction.amount)
        }else if (transaction.type.value.equals("d", ignoreCase = true)) {
            client.debit(transaction.amount)
        }else{
            throw InvalidParameterException()
        }

    }

}