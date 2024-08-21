package com.felipejdias.rinhabackend2024q1.service

import com.felipejdias.rinhabackend2024q1.model.Client
import org.springframework.stereotype.Service

@Service
interface ClientService {

    fun getClientById(id: Long) : Client

    fun createClient(client: Client) : Client

}