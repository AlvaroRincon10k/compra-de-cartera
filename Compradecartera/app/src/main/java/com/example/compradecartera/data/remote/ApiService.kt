package com.example.compradecartera.data.remote

import com.example.compradecartera.FinalizeTransactionResponse
import com.example.compradecartera.TransactionNumberResponse
import com.example.compradecartera.domain.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getUser(@Url url:String): Response<UserResponse>

    @GET("get_transaction_id")
    suspend fun getTransactionNumber(): Response<TransactionNumberResponse>

    @GET
    suspend fun getFinalizeTransaction(@Url url:String): Response<FinalizeTransactionResponse>
}