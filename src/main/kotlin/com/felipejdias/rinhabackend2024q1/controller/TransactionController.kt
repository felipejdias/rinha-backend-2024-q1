package com.felipejdias.rinhabackend2024q1.controller

import com.felipejdias.rinhabackend2024q1.context.Context
import com.felipejdias.rinhabackend2024q1.db.model.Transaction
import com.felipejdias.rinhabackend2024q1.exchange.TransactionRequest
import com.felipejdias.rinhabackend2024q1.service.TransactionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transactions")
class TransactionController {

    @Qualifier("transactionService")
    @Autowired
    private lateinit var transactionService: TransactionService

    @PostMapping("/create/{clientId}")
    fun createTransaction(
        @PathVariable("clientId") clientId: Long,
        @RequestBody transaction: TransactionRequest): Transaction {
        return transactionService.create(Context(clientId = clientId, request = transaction))
    }

    @GetMapping("/{id}")
    fun getTransactionById(@PathVariable("id") id: Long){

    }


}