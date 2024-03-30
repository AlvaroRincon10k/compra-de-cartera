package com.example.compradecartera.domain.repository

import com.example.compradecartera.FinalizeTransactionResponse
import com.example.compradecartera.TransactionNumberResponse
import com.example.compradecartera.domain.model.BinCheckerResponse

interface CardNumberRepository {

    suspend fun getTransactionNumber(): TransactionNumberResponse

    suspend fun getFinalizeTransaction(id:String): FinalizeTransactionResponse

    suspend fun getBinChecker(bin:String): BinCheckerResponse
}
