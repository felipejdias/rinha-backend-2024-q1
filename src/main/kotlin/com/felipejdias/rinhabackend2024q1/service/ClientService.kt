package com.felipejdias.rinhabackend2024q1.service

import com.felipejdias.rinhabackend2024q1.db.model.Client
import java.util.*

interface ClientService {

    fun findById(id: Long) : Optional<Client>

    fun createOrUpdateClient(client: Client) : Client

}