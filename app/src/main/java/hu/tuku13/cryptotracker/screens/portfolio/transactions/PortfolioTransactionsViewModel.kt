package hu.tuku13.cryptotracker.screens.portfolio.transactions

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import hu.tuku13.cryptotracker.database.getDatabase
import hu.tuku13.cryptotracker.domain.PortfolioTransaction
import hu.tuku13.cryptotracker.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PortfolioTransactionsViewModel(
    val application: Application
) : ViewModel() {
    private val coinRepository = CoinRepository(getDatabase(application), application)
    val coins = coinRepository.coins
    val transactions = coinRepository.portfolioTransactions

    init {
        viewModelScope.launch(Dispatchers.IO) {
            coinRepository.loadCoins(5000)
            coinRepository.loadPortfolio()
        }
    }

    fun deleteTransaction(transaction: PortfolioTransaction) {
        viewModelScope.launch(Dispatchers.IO) {
            coinRepository.deleteTransaction(transaction)
            coinRepository.loadPortfolio()
        }
    }

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