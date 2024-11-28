package com.example.feature.currency.model

data class CurrencyUiState(
    val balances: MutableMap<String, Double> = mutableMapOf("EUR" to 1000.0),
    val amount: String = "",
    val fromCurrency: String = "EUR",
    val toCurrency: String = "USD",
    val message: String = "",
    val receivedAmount: String = "",
    val rates: Map<String, Double> = emptyMap()
)

