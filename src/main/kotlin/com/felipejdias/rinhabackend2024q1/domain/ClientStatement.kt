package com.felipejdias.rinhabackend2024q1.domain

import java.time.Instant

data class ClientStatement(
    val balance: Balance,
    val lastTransactions: List<TransactionDomain>
    ){
    //TODO criar um domainToBody

}

data class Balance(
    val total: Long,
    val date: Instant,
    val limit: Long,
    ){

    //TODO criar um método domainToBody

}
