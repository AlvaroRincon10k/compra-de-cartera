package com.example.compradecartera.data.remote.repository

import com.example.compradecartera.FinalizeTransactionResponse
import com.example.compradecartera.TransactionNumberResponse
import com.example.compradecartera.data.remote.TransactionNumberDataSource
import com.example.compradecartera.domain.model.UserResponse
import com.example.compradecartera.domain.repository.UserRepository

class TransactionNumberRepositoryImpl(
    private val transactionNumberDataSource: TransactionNumberDataSource
): UserRepository {
    override suspend fun getUser(name: String): UserResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getTransactionNumber(): TransactionNumberResponse = transactionNumberDataSource.getTransactionNumber()
    override suspend fun getFinalizeTransaction(id: String): FinalizeTransactionResponse {
        TODO("Not yet implemented")
    }
}
