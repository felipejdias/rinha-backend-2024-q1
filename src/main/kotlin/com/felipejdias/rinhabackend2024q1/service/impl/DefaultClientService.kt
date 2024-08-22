package com.felipejdias.rinhabackend2024q1.service.impl

import com.felipejdias.rinhabackend2024q1.db.model.Client
import com.felipejdias.rinhabackend2024q1.db.repository.ClientRepository
import com.felipejdias.rinhabackend2024q1.service.ClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class DefaultClientService: ClientService {

    @Autowired
    private lateinit var repository: ClientRepository

    override fun findById(id: Long): Optional<Client> {
        return repository.findById(id)
    }

    override fun createClient(client: Client): Client {
        return repository.save(client)
    }


}