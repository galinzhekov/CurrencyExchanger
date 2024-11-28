package com.example.core.domain.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.core.domain.logic.BaseViewModel

@Composable
fun <T: Any>BaseView(
    viewModel: BaseViewModel<T>,
    modifier                    : Modifier                  = Modifier,
    horizontalAlignment         : Alignment.Horizontal      = Alignment.CenterHorizontally,
    verticalArrangement         : Arrangement.Vertical      = Arrangement.Top,
    content: @Composable ColumnScope.(T) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = Color.LightGray
    ) {
        Column(
            modifier = Modifier.padding(it).then(modifier),
            horizontalAlignment = horizontalAlignment,
            verticalArrangement = verticalArrangement
        ) {
            content(uiState)
        }
    }


}