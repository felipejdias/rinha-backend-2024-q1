package com.felipejdias.rinhabackend2024q1.repository

import com.felipejdias.rinhabackend2024q1.model.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : JpaRepository<Client, Long> {
}