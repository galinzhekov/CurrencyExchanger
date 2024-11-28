package com.example.core.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("currency-exchange-rates")
    suspend fun getExchangeRates(): ExchangeRatesResponseModel
}

val retrofit = Retrofit.Builder()
    .baseUrl("https://developers.paysera.com/tasks/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService: ApiService = retrofit.create(ApiService::class.java)