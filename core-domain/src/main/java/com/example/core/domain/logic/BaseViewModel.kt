package com.example.core.domain.logic

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<T : Any>(initialUiState: T) : ViewModel() {
    private val _uiState = MutableStateFlow(initialUiState)
    val uiState: StateFlow<T> = _uiState

    protected fun updateUiState(update: (T) -> T) {
        _uiState.value.let { currentState ->
            _uiState.value = update(currentState)
        }
    }
}