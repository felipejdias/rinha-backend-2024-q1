package com.felipejdias.rinhabackend2024q1.service.impl

import com.felipejdias.rinhabackend2024q1.db.model.toTransacoes
import com.felipejdias.rinhabackend2024q1.domain.ExtratoBancario
import com.felipejdias.rinhabackend2024q1.domain.Saldo
import com.felipejdias.rinhabackend2024q1.service.ClientService
import com.felipejdias.rinhabackend2024q1.service.StatementService
import com.felipejdias.rinhabackend2024q1.service.TransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class DefaultStatementService: StatementService {

    @Autowired
    private lateinit var transactionService: TransactionService

    @Autowired
    private lateinit var clientService: ClientService

    override fun getClientStatement(clientId: Long): ExtratoBancario {
        val client  = clientService.findById(clientId)
        val lastTransactions = transactionService.getAllTransactionsByClient(clientId)
            .orElseThrow { HttpClientErrorException(NOT_FOUND, "Transactions not found") }
        val actualBalance= transactionService.calculateNewClientBalance(client)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val formattedDateTime = formatter.format( Instant.now().atZone(ZoneId.systemDefault()))
        val saldo = Saldo(total = actualBalance, data_extrato = formattedDateTime, limite = client.limit)

        return ExtratoBancario(saldo = saldo,  ultimas_transacoes =  lastTransactions.toTransacoes())

    }
}