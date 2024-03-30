package com.example.compradecartera.data.remote

import com.example.compradecartera.FinalizeTransactionResponse
import com.example.compradecartera.TransactionNumberResponse
import com.example.compradecartera.domain.model.BinCheckerResponse
import com.example.compradecartera.domain.model.UserResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET("v2/0ms8mu9j/user/{name}")
    suspend fun getUser(@Path("name") url:String): Response<UserResponse>

    @GET("v2/0ms8mu9j/get_transaction_id")
    suspend fun getTransactionNumber(): Response<TransactionNumberResponse>

    @GET("v2/0ms8mu9j/confirm/{transaction_id}")
    suspend fun getFinalizeTransaction(@Path("transaction_id") url:String): Response<FinalizeTransactionResponse>

    @Headers(
        "content-type: application/json",
        "X-RapidAPI-Host: bin-ip-checker.p.rapidapi.com",
        "X-RapidAPI-Key: fefaa3a930mshb8971d4a52078cfp1d07c2jsnf1eb0d34194f"
    )
    @POST("/")
    suspend fun getBinChecker(@Query("bin") bin: String): Response<BinCheckerResponse>
}