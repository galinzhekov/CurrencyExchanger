package com.example.feature.currency.repository

import com.example.core.network.ApiService
import com.example.core.utils.CurrencyConversionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun provideCurrencyRepository(apiService: ApiService): CurrencyRepository {
        return CurrencyRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideCurrencyConversionUseCase(): CurrencyConversionUseCase {
        return CurrencyConversionUseCase()
    }
}
