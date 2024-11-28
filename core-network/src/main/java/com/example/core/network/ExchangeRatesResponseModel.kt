package com.example.core.network

data class ExchangeRatesResponseModel(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)