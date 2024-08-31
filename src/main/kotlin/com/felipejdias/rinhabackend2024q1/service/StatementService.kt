package com.felipejdias.rinhabackend2024q1.service

import com.felipejdias.rinhabackend2024q1.domain.ExtratoBancario

interface StatementService {

    fun getClientStatement(clientId: Long): ExtratoBancario
}