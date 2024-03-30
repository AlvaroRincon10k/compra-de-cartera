package com.example.compradecartera.data.remote.repository

import com.example.compradecartera.FinalizeTransactionResponse
import com.example.compradecartera.TransactionNumberResponse
import com.example.compradecartera.data.remote.BinCheckerDataSource
import com.example.compradecartera.data.remote.FinalizeTransactionDataSource
import com.example.compradecartera.data.remote.TransactionNumberDataSource
import com.example.compradecartera.domain.model.BinCheckerResponse
import com.example.compradecartera.domain.model.UserResponse
import com.example.compradecartera.domain.repository.CardNumberRepository
import com.example.compradecartera.domain.repository.UserRepository

class BinCheckerRepositoryImpl(
    private val binCheckerDataSource: BinCheckerDataSource,
    private val transactionNumberDataSource: TransactionNumberDataSource,
    private val finalizeTransactionDataSource: FinalizeTransactionDataSource
) : CardNumberRepository {

    override suspend fun getTransactionNumber(): TransactionNumberResponse =
        transactionNumberDataSource.getTransactionNumber()

    override suspend fun getFinalizeTransaction(id: String): FinalizeTransactionResponse =
        finalizeTransactionDataSource.getFinalizeTransaction(id)

    override suspend fun getBinChecker(bin: String): BinCheckerResponse =
        binCheckerDataSource.getBinChecker(bin)
}