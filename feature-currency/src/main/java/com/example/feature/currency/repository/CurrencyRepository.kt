package com.example.feature.currency.repository

import com.example.core.network.ApiService
import com.example.core.network.ExchangeRatesResponseModel
import javax.inject.Inject

interface CurrencyRepository {
    suspend fun fetchExchangeRates(): ExchangeRatesResponseModel
}

class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CurrencyRepository {
    override suspend fun fetchExchangeRates(): ExchangeRatesResponseModel {
        return apiService.getExchangeRates()
    }
}
