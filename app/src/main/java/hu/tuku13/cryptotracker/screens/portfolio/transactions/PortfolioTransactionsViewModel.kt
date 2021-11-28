package hu.tuku13.cryptotracker.screens.portfolio.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import hu.tuku13.cryptotracker.domain.PortfolioTransaction
import hu.tuku13.cryptotracker.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PortfolioTransactionsViewModel(
    private val coinRepository: CoinRepository
) : ViewModel() {

    val coins = coinRepository.coins
    val transactions = coinRepository.portfolioTransactions

    init {
        viewModelScope.launch(Dispatchers.IO) {
            coinRepository.loadCoins(500)
            coinRepository.loadPortfolio()
        }
    }

    fun deleteTransaction(transaction: PortfolioTransaction) {
        viewModelScope.launch(Dispatchers.IO) {
            coinRepository.deleteTransaction(transaction)
            coinRepository.loadPortfolio()
            coinRepository.loadCoinWithTransactions()
        }
    }

    class Factory(
        private val coinRepository: CoinRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(PortfolioTransactionsViewModel::class.java)) {
                return PortfolioTransactionsViewModel(coinRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}