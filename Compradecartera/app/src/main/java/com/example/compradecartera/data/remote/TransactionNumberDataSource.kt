package com.example.compradecartera.data.remote

import com.example.compradecartera.TransactionNumberResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class TransactionNumberDataSource(
    private val coroutineContext: CoroutineContext = Dispatchers.IO,
    private val apiService: ApiService
)  {

    suspend fun getTransactionNumber(): TransactionNumberResponse {
        val response = withContext(coroutineContext) {
            apiService.getTransactionNumber()
        }

        val body = response.body()

        if(response.isSuccessful && body is TransactionNumberResponse) {
            return body
        } else {
            error("")
        }
    }
}