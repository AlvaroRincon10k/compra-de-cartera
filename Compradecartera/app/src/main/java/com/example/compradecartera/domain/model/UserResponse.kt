package com.example.compradecartera.domain.model

data class UserResponse(
    val amount: Double,
    val card_number: String,
    val name: String
)