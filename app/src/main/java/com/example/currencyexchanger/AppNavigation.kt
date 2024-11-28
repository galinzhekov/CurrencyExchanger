package com.example.currencyexchanger

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.feature.currency.views.CurrencyConverterScreen

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "currency_exchange"
    ) {
        composable("currency_exchange") {
            CurrencyConverterScreen()
        }
    }
}