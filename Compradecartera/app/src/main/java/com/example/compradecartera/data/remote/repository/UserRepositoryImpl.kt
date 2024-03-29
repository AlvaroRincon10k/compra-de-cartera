package com.example.compradecartera.data.remote.repository

import com.example.compradecartera.FinalizeTransactionResponse
import com.example.compradecartera.TransactionNumberResponse
import com.example.compradecartera.data.remote.UserDataSource
import com.example.compradecartera.domain.model.UserResponse
import com.example.compradecartera.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userDataSource: UserDataSource
): UserRepository {

    override suspend fun getUser(name:String): UserResponse = userDataSource.getUser(name)
    override suspend fun getTransactionNumber(): TransactionNumberResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getFinalizeTransaction(id: String): FinalizeTransactionResponse {
        TODO("Not yet implemented")
    }
}