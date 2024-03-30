package com.example.compradecartera.data.remote

import com.example.compradecartera.FinalizeTransactionResponse
import com.example.compradecartera.domain.model.BinCheckerResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Callback
import retrofit2.Call
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class BinCheckerDataSource(
    private val coroutineContext: CoroutineContext = Dispatchers.IO,
    private val apiService: ApiService
)  {

    suspend fun getBinChecker(bin: String): BinCheckerResponse {
        val response = withContext(coroutineContext) {
           apiService.getBinChecker(bin)
        }

        val body = response.body()

        if(response.isSuccessful && body is BinCheckerResponse) {
            return body
        } else {
            error("")
        }
    }
}
