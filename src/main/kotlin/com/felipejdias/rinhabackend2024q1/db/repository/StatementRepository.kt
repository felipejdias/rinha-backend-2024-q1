package com.felipejdias.rinhabackend2024q1.db.repository

import com.felipejdias.rinhabackend2024q1.db.connection.DatabaseConnection
import com.felipejdias.rinhabackend2024q1.db.model.TransactionEntity
import com.felipejdias.rinhabackend2024q1.db.model.toTransactionsStatement
import com.felipejdias.rinhabackend2024q1.domain.ClientStatement
import com.felipejdias.rinhabackend2024q1.exception.ClientNotFoundException
import java.sql.PreparedStatement
import java.time.LocalDateTime

class StatementRepository {

    companion object {
        val QUERY = """SELECT c.balance, c.credit_limit, t.amount, t.type, t.created_at, t.description
                FROM client c 
                LEFT JOIN transactions t ON c.client_id = t.client_id 
                WHERE c.client_id = ? ORDER BY t.created_at DESC limit 10
            """.trimMargin()
    }

    fun calculateClientStatement(clientId: Long): ClientStatement {
        var preparedStatement: PreparedStatement
        var clientStatement = ClientStatement()
        var transactionsList = ArrayList<TransactionEntity>()

        DatabaseConnection.getDataSource().connection.use { connection ->
            preparedStatement = connection.prepareStatement(QUERY)
            preparedStatement.setLong(1, clientId)

            preparedStatement.executeQuery().use {
                while (it.next()) {
                    clientStatement.balance.total = it.getLong("balance")
                    clientStatement.balance.limit = it.getLong("credit_limit")
                    clientStatement.balance.statementDate = LocalDateTime.now().toString()

                    val transaction = TransactionEntity.Builder()
                        .type(it.getString("type").orEmpty())
                        .amount( it.getLong("amount").or(0))
                        .description(it.getString("description").orEmpty())
                        .clientId(clientId)
                        .createdAt(it.getString("created_at").orEmpty())
                        .build()
                    transactionsList.add(transaction)
                }
            }

        }
        if (clientStatement.balance.limit == 0L && clientStatement.balance.total == 0L && clientStatement.recentTransactions.isEmpty()) throw ClientNotFoundException()
        clientStatement.recentTransactions = transactionsList.toTransactionsStatement()
        return clientStatement
    }
}