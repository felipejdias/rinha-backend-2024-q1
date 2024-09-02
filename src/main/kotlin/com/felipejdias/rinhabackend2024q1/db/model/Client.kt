package com.felipejdias.rinhabackend2024q1.db.model

import com.felipejdias.rinhabackend2024q1.exception.ClientLimitExceededException
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import kotlin.math.abs

@Entity
data class Client(
    @Id
    @Column(name = "client_id")
    val id: Long,
    val name: String,
    @Column(name = "credit_limit")
    var limit: Long,
    @Column(name = "balance")
    var balance: Long = 0){
    override fun toString(): String {
        return "Client(id=$id, name='$name', limit=$limit, balance=$balance)"
    }
    fun credit(value: Long){
        this.balance = this.balance.plus(value)
    }

    fun debit(value: Long){
        val newBalance = this.balance.minus(value)
        if(newBalance < 0 && abs(newBalance) > this.limit){
            throw ClientLimitExceededException()
        }
        this.balance = newBalance
    }
}
