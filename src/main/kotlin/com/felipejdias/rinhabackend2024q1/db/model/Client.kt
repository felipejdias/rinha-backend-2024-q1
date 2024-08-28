package com.felipejdias.rinhabackend2024q1.db.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id

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
}
