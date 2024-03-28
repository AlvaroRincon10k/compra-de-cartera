package com.example.compradecartera.domain.repository

import com.example.compradecartera.domain.model.UserResponse

interface UserRepository {

    suspend fun getUser(name:String): UserResponse
}