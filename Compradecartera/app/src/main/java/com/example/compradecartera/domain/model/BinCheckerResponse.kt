package com.example.compradecartera.domain.model

data class BinCheckerResponse(
    val BIN: BIN,
    val code: Int,
    val success: Boolean
)