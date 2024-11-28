package com.example.feature.currency.logic

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.core.domain.logic.BaseViewModel
import com.example.core.utils.CommissionRule
import com.example.core.utils.CurrencyConversionUseCase
import com.example.feature.currency.model.CurrencyUiState
import com.example.feature.currency.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepository,
    private val conversionUseCase: CurrencyConversionUseCase
) : BaseViewModel<CurrencyUiState>(CurrencyUiState()) {

    init {
        refreshRates()
        startPeriodicRateFetching()
    }

    // Function to fetch rates once
    fun refreshRates() {
        viewModelScope.launch {
            updateUiState { it.copy(isLoading = true) } // Show loading state
            try {
                val response = repository.fetchExchangeRates()
                updateUiState { state ->
                    state.copy(
                        rates = response.rates,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                updateUiState { state ->
                    state.copy(
                        isLoading = false
                    )
                }
            }
        }
    }

    // Function to start periodic fetching of exchange rates
    private fun startPeriodicRateFetching() {
        viewModelScope.launch {
            while (true) {
                try {
                    val response = withContext(Dispatchers.IO) {
                        repository.fetchExchangeRates()
                    }
                    updateUiState { state ->
                        state.copy(rates = response.rates) // Update only the rates
                    }
                    Log.d("DropdownOptions", response.rates.toString())
                } catch (e: Exception) {
                    // Log or handle the error, but don't break the loop
                }
                delay(5000) // Wait for 5 seconds before fetching again
            }
        }
    }

    fun updateAmount(amount: String) {
        updateUiState { it.copy(amount = amount) }
    }

    fun updateFromCurrency(currency: String) {
        updateUiState { it.copy(fromCurrency = currency) }
    }

    fun updateToCurrency(currency: String) {
        updateUiState { it.copy(toCurrency = currency) }
    }

    fun convertCurrency(amountString: String, fromCurrency: String, toCurrency: String) {
        val amount = amountString.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            updateUiState { it.copy(message = "Please enter a valid amount.") }
            return
        }

        val rate = uiState.value.rates[toCurrency]
        if (rate == null) {
            updateUiState { it.copy(message = "Exchange rate not available for $toCurrency.") }
            return
        }

        val result = conversionUseCase.calculateConversion(
            amount = amount,
            rate = rate,
            commissionRule = CommissionRule.FreeFirstFive
        )

        val currentBalances = uiState.value.balances.toMutableMap()
        val fromBalance = currentBalances[fromCurrency] ?: 0.0

        if (fromBalance < amount + result.commission) {
            updateUiState { it.copy(message = "Insufficient balance in $fromCurrency.") }
            return
        }

        // Perform the balance updates
        currentBalances[fromCurrency] = fromBalance - amount - result.commission
        currentBalances[toCurrency] = (currentBalances[toCurrency] ?: 0.0) + result.convertedAmount

        // Update the UI state with conversion details
        updateUiState {
            it.copy(
                balances = currentBalances,
                receivedAmount = "%.2f".format(result.convertedAmount),
                message = "Successfully converted $amount $fromCurrency to ${result.convertedAmount} $toCurrency! Commission Fee: ${"%.2f".format(result.commission)} $fromCurrency."
            )
        }
    }
}
