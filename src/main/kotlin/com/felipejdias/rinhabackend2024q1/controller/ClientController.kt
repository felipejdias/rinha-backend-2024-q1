package com.felipejdias.rinhabackend2024q1.controller

import com.felipejdias.rinhabackend2024q1.db.model.Client
import com.felipejdias.rinhabackend2024q1.service.ClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Optional

@RestController
@RequestMapping("/clients")
class ClientController {

    @Autowired
    private lateinit var clientService: ClientService

    @GetMapping("/{id}")
    fun getClientById(@PathVariable id: Long) : Optional<Client> {
        //TODO tratar pra quando nao encontrar o client na base retornar um notFound HTTP_STATUS_CODE 404
        return clientService.findById(id)
    }

    @PostMapping("/create")
    fun createClient(@RequestBody client: Client): Client {
        //TODO ainda nao está gerando ID auto increment verificar o erro org.springframework.dao.InvalidDataAccessResourceUsageException: could not extract ResultSet [ERROR: relation "client_seq" does not exist
        return clientService.createClient(client)
    }
}