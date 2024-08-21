package com.felipejdias.rinhabackend2024q1.repository

import com.felipejdias.rinhabackend2024q1.model.Client
import org.springframework.data.jpa.repository.JpaRepository


interface ClientRepository : JpaRepository<Client, Long>