package com.example.core.utils

import com.example.core.utils.model.ConversionResult
import javax.inject.Inject

class CurrencyConversionUseCase @Inject constructor() {
    fun calculateConversion(
        amount: Double,
        rate: Double,
        commissionRule: CommissionRule
    ): ConversionResult {
        val convertedAmount = amount * rate
        val commission = commissionRule.calculateCommission(amount)
        return ConversionResult(convertedAmount, commission)
    }
}