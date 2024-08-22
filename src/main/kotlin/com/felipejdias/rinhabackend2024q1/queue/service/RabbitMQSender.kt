package com.felipejdias.rinhabackend2024q1.queue.service

import com.felipejdias.rinhabackend2024q1.db.model.Transaction
import com.felipejdias.rinhabackend2024q1.queue.beans.RabbitMQBean
import org.springframework.stereotype.Service

@Service
class RabbitMQSender {

    private var bean = RabbitMQBean()

    val QUEUE_NAME= "transactions.queue"

    fun send(transaction: Transaction) {
      bean.connectionFactory().basicPublish("", QUEUE_NAME, null, transaction.toString().toByteArray())
    }

}