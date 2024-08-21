package com.felipejdias.rinhabackend2024q1.db.repository

import com.felipejdias.rinhabackend2024q1.db.model.Client
import org.springframework.data.jpa.repository.JpaRepository


interface ClientRepository : JpaRepository<Client, Long>