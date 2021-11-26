package hu.tuku13.cryptotracker.screens.portfolio.transactions

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PortfolioTransactionsViewModel(
    val application: Application
) : ViewModel() {

    class Factory(
        private val application: Application
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(PortfolioTransactionsViewModel::class.java)) {
                return PortfolioTransactionsViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}