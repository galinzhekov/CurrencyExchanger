package com.example.core.utils

sealed class CommissionRule {
    abstract fun calculateCommission(amount: Double): Double

    data object FreeFirstFive : CommissionRule() {
        private var freeConversionsLeft = 5
        override fun calculateCommission(amount: Double): Double {
            return if (freeConversionsLeft > 0) {
                freeConversionsLeft--
                0.0
            } else {
                0.007 * amount
            }
        }
    }

    data class FlatRate(val percentage: Double) : CommissionRule() {
        override fun calculateCommission(amount: Double) = percentage * amount
    }
}