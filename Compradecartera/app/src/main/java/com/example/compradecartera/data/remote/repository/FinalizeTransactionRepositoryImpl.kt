package com.example.compradecartera.data.remote.repository

import com.example.compradecartera.FinalizeTransactionResponse
import com.example.compradecartera.TransactionNumberResponse
import com.example.compradecartera.data.remote.FinalizeTransactionDataSource
import com.example.compradecartera.domain.model.UserResponse
import com.example.compradecartera.domain.repository.UserRepository

class FinalizeTransactionRepositoryImpl(
    private val finalizeTransactionDataSource: FinalizeTransactionDataSource
): UserRepository {

    override suspend fun getFinalizeTransaction(id: String): FinalizeTransactionResponse = finalizeTransactionDataSource.getFinalizeTransaction(id)
    override suspend fun getUser(name: String): UserResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getTransactionNumber(): TransactionNumberResponse {
        TODO("Not yet implemented")
    }
}