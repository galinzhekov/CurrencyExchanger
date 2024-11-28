package com.example.feature.currency.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.domain.views.BaseView
import com.example.core.ui.components.CurrencyDropdownMenu
import com.example.feature.currency.R
import com.example.feature.currency.logic.CurrencyViewModel

@Composable
fun CurrencyConverterScreen(viewModel: CurrencyViewModel = hiltViewModel()) {

    BaseView(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        viewModel = viewModel,
        modifier = Modifier.padding(16.dp).imePadding()
    ) { uiState ->
        Text(
            stringResource(R.string.currency_converter),
            style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "MY BALANCES",
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Display balances
                LazyRow(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    item {
                        uiState.balances.forEach { (currency, balance) ->
                            Text(
                                text = "${"%.2f".format(balance)} $currency ",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }

                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    stringResource(R.string.currency_exchange),
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Sell section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Sell",
                        tint = Color.Red,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Sell", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.weight(1f))

                    TextField(
                        value = uiState.amount,
                        onValueChange = { amount -> viewModel.updateAmount(amount) },
                        singleLine = true,
                        modifier = Modifier.width(100.dp),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.End),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    CurrencyDropdownMenu(
                        label = "",
                        options = uiState.balances.keys.toList(),
                        selectedOption = uiState.fromCurrency,
                        onOptionSelected = { fromCurrency -> viewModel.updateFromCurrency(fromCurrency) }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Receive section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Receive",
                        tint = Color.Green,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Receive", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        "+${uiState.receivedAmount}",
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.Green),
                        modifier = Modifier.width(100.dp),
                        textAlign = TextAlign.End
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    CurrencyDropdownMenu(
                        label = "",
                        options = uiState.rates.keys.toList(),
                        selectedOption = uiState.toCurrency,
                        onOptionSelected = { toCurrency -> viewModel.updateToCurrency(toCurrency) }
                    )
                }
            }
        }

        Text(
            uiState.message,
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                viewModel.convertCurrency(uiState.amount, uiState.fromCurrency, uiState.toCurrency)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF008CFF),
                contentColor = Color.White
            )
        ) {
            Text(stringResource(R.string.submit))
        }


    }
}