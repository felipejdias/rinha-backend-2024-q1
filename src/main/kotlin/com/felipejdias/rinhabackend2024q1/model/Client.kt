package com.felipejdias.rinhabackend2024q1.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val name: String,
    val limit: Int) {
    override fun toString(): String {
        return "Client(id=$id, name='$name', limit=$limit)"
    }
}
