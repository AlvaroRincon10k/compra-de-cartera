package com.example.compradecartera.domain.repository

import com.example.compradecartera.FinalizeTransactionResponse
import com.example.compradecartera.TransactionNumberResponse
import com.example.compradecartera.domain.model.UserResponse

interface UserRepository {

    suspend fun getUser(name:String): UserResponse
}