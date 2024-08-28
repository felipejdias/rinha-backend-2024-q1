package com.felipejdias.rinhabackend2024q1.controller

import com.felipejdias.rinhabackend2024q1.db.model.Client
import com.felipejdias.rinhabackend2024q1.domain.ExtratoBancario
import com.felipejdias.rinhabackend2024q1.service.ClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/clientes")
class ClientController {

    @Autowired
    private lateinit var clientService: ClientService

    @GetMapping("/{id}")
    fun getClientById(@PathVariable id: Long) : Optional<Client> {
        return clientService.findById(id)
    }

    @PostMapping("/create")
    fun createClient(@RequestBody client: Client): Client {
       return clientService.createClient(client)
    //TODO nao funciona ainda pois já foi pré
    // inserido na base de dados  alguns registros precisa bolar um jeito do
    // primeiro a ser inserido já começar no 7 pois na doc oficial fala q o 6 especificamente nao deve ser utilizado

    }

    @GetMapping("/{id}/extrato")
    fun getClientStatements(@PathVariable id: Long): Optional<ExtratoBancario> {
        TODO("Not yet implemented")
        //TODO precisa ser implementada a lógica de obter o extrator
    }
}