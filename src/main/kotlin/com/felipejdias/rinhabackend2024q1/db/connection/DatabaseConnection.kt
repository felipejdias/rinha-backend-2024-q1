package com.felipejdias.rinhabackend2024q1.db.connection
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection
import java.sql.SQLException

class DatabaseConnection {
    companion object {
        private val hikariConfig: HikariConfig = HikariConfig().apply {
            jdbcUrl = "jdbc:postgresql://db:5432/rinha-backend"
            username = "admin"
            password = "123"
            driverClassName = "org.postgresql.Driver"
            maximumPoolSize = 20
            idleTimeout = 30000
        }

        private val dataSource: HikariDataSource = HikariDataSource(hikariConfig)

        @Throws(SQLException::class)
        fun getConnection(): Connection {
            return dataSource.connection
        }

        fun close() {
            if (!dataSource.isClosed) {
                dataSource.close()
            }
        }
    }
}