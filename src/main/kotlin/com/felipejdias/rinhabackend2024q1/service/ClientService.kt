package com.felipejdias.rinhabackend2024q1.service

import com.felipejdias.rinhabackend2024q1.db.model.Client
import com.felipejdias.rinhabackend2024q1.domain.ExtratoBancario
import org.springframework.stereotype.Service
import java.util.*

@Service
interface ClientService {

    fun findById(id: Long) : Optional<Client>

    fun createClient(client: Client) : Client

    fun getClientStatement(clientId: Long): ExtratoBancario

}