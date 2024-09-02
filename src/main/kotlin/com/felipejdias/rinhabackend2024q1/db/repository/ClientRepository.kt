package com.felipejdias.rinhabackend2024q1.db.repository

import com.felipejdias.rinhabackend2024q1.db.model.Client
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ClientRepository : JpaRepository<Client, Long>{
    @Query("SELECT c FROM Client c WHERE c.id = :id")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findClientAndLock(id: Long): Optional<Client>
}