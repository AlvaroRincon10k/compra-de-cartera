package com.example.compradecartera.domain.model

data class Country(
    val alpha2: String,
    val alpha3: String,
    val capital: String,
    val currency: String,
    val currency_symbol: String,
    val flag: String,
    val idd: String,
    val language: String,
    val language_code: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val native: String,
    val numeric: String,
    val region: String,
    val subregion: String
)