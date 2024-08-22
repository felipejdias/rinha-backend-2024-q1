package com.felipejdias.rinhabackend2024q1.queue.beans

import com.rabbitmq.client.Channel
import com.rabbitmq.client.ConnectionFactory
import org.springframework.context.annotation.Bean


class RabbitMQBean {

    @Bean
    fun connectionFactory(): Channel {
        val factory = ConnectionFactory()
            .apply {
                username = "rabbitmq"
                password = "rabbitmq"
                host = ConnectionFactory.DEFAULT_HOST
                virtualHost = ConnectionFactory.DEFAULT_VHOST
                port = ConnectionFactory.DEFAULT_AMQP_PORT
            }

        val channel = factory.newConnection().createChannel()

        channel.queueDeclare("transaction.queue", false, false, false, null)

        return channel
    }
}