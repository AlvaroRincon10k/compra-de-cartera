package com.example.compradecartera.domain.model

data class BIN(
    val brand: String,
    val country: Country,
    val currency: String,
    val issuer: Issuer,
    val length: Int,
    val level: String,
    val number: Int,
    val scheme: String,
    val type: String,
    val valid: Boolean
)