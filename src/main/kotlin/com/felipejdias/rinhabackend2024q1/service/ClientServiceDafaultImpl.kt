package com.felipejdias.rinhabackend2024q1.service

import com.felipejdias.rinhabackend2024q1.model.Client
import com.felipejdias.rinhabackend2024q1.repository.ClientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class ClientServiceDafaultImpl: ClientService {

    @Autowired
    lateinit var repository: ClientRepository

    override fun getClientById(id: Long): Client {
        return repository.findById(id).let { throw NotFoundException() }
    }

    override fun createClient(client: Client): Client {
        return repository.save(client)
    }


}