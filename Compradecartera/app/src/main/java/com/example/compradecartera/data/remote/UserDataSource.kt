package com.example.compradecartera.data.remote

import com.example.compradecartera.data.remote.ApiService
import com.example.compradecartera.domain.model.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class UserDataSource(
    private val coroutineContext: CoroutineContext = Dispatchers.IO,
    private val apiService: ApiService
) {
    suspend fun getUser(name: String): UserResponse {
        val response = withContext(coroutineContext) {
            apiService.getUser(name)
        }

        val body = response.body()

        if(response.isSuccessful && body is UserResponse) {
            return body
        } else {
            error("")
        }
    }
}