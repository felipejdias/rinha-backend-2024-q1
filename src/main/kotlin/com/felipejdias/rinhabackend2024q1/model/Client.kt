package com.felipejdias.rinhabackend2024q1.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Client(
    @Id
    val id: Long,
    val name: String,
    val limit: Int)
