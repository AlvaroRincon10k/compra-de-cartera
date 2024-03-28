package com.example.compradecartera

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getUser(@Url url:String): Response<UserResponse>
    @GET("get_transaction_id")
    suspend fun getTransactionNumber(): Response<TransactionNumberResponse>
}