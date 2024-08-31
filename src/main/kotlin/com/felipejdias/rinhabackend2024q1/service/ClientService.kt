package com.felipejdias.rinhabackend2024q1.service

import com.felipejdias.rinhabackend2024q1.db.model.Client

interface ClientService {

    fun findById(id: Long) : Client

    fun createOrUpdateClient(client: Client) : Client

}