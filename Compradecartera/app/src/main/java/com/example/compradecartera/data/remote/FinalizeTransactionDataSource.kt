package com.example.compradecartera.data.remote

import com.example.compradecartera.FinalizeTransactionResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class FinalizeTransactionDataSource (
    private val coroutineContext: CoroutineContext = Dispatchers.IO,
    private val apiService: ApiService
) {

    suspend fun getFinalizeTransaction(id: String): FinalizeTransactionResponse {
        val response = withContext(coroutineContext) {
            apiService.getFinalizeTransaction(id)
        }

        val body = response.body()

        if(response.isSuccessful && body is FinalizeTransactionResponse) {
            return body
        } else {
            return FinalizeTransactionResponse("null")
            error("")
        }
    }
}