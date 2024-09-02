package com.felipejdias.rinhabackend2024q1.db.repository

import com.felipejdias.rinhabackend2024q1.db.connection.DatabaseConnection
import com.felipejdias.rinhabackend2024q1.db.model.TransactionEntity
import com.felipejdias.rinhabackend2024q1.domain.ClientStatement
import com.felipejdias.rinhabackend2024q1.domain.TransactionResponse
import com.felipejdias.rinhabackend2024q1.exception.ClientNotFoundException
import com.felipejdias.rinhabackend2024q1.exchange.ClientResponse
import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.SQLException
import java.time.LocalDateTime

class TransactionRepository {

    fun registerTransaction(clientId: Long, transactionEntity: TransactionEntity): ClientResponse {
        DatabaseConnection.getConnection().use { connection ->
            val procedureCall = "{call public.register_transaction(?, ?, ?, ?)}"
            val callableStatement: CallableStatement = connection.prepareCall(procedureCall)

            callableStatement.setInt(1, clientId.toInt())
            callableStatement.setString(2, transactionEntity.type)
            callableStatement.setLong(3, transactionEntity.amount)
            callableStatement.setString(4, transactionEntity.description)

            callableStatement.executeQuery().use { resultSet ->
                if (resultSet.next()) {
                    return ClientResponse(resultSet.getLong("limiteRetorno"), resultSet.getLong("saldoRetorno"))
                } else {
                    throw SQLException("Register transaction failed, no rows affected.")
                }
            }
        }
    }

    fun calculateClientStatement(clientId: Long): ClientStatement {
        DatabaseConnection.getConnection().use { connection ->
            val query = """SELECT c.balance, c.credit_limit, t.amount, t.type, t.created_at, t.description
                           FROM client c 
                           LEFT JOIN transactions t ON c.client_id = t.client_id 
                           WHERE c.client_id = ? 
                           ORDER BY t.created_at DESC 
                           LIMIT 10"""
            val preparedStatement: PreparedStatement = connection.prepareStatement(query)
            preparedStatement.setLong(1, clientId)

            preparedStatement.executeQuery().use { resultSet ->
                val clientStatement = ClientStatement()
                var transactionsList = ArrayList<TransactionResponse>()

                while (resultSet.next()) {
                    if (resultSet.isFirst) {
                        clientStatement.balance.total = resultSet.getLong("balance")
                        clientStatement.balance.limit = resultSet.getLong("credit_limit")
                        clientStatement.balance.statementDate = LocalDateTime.now().toString()
                    }
                    val transaction = TransactionResponse.Builder()
                        .type(resultSet.getString("type") ?: "")
                        .amount(resultSet.getLong("amount"))
                        .description(resultSet.getString("description") ?: "")
                        .createdAt(resultSet.getString("created_at") ?: "")
                        .build()
                    transactionsList.add(transaction)
                }
                if (clientStatement.balance.limit == 0L && clientStatement.balance.total == 0L && transactionsList.isEmpty()) {
                    throw ClientNotFoundException()
                }
                clientStatement.recentTransactions = transactionsList
                return clientStatement
            }
        }
    }
}