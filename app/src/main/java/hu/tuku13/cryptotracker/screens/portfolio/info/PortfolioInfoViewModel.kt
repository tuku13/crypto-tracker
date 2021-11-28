package hu.tuku13.cryptotracker.screens.portfolio.info

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import hu.tuku13.cryptotracker.database.getDatabase
import hu.tuku13.cryptotracker.domain.PortfolioTransaction
import hu.tuku13.cryptotracker.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PortfolioInfoViewModel(
    private val application: Application
): ViewModel() {
    private val coinRepository = CoinRepository(getDatabase(application), application)
    val coinWithPortfolioTransactions = coinRepository.coinWithTransaction

    init {
        viewModelScope.launch(Dispatchers.IO){
            coinRepository.loadCoinWithTransactions()
        }
    }

    class Factory(
        private val application: Application
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(PortfolioInfoViewModel::class.java)) {
                return PortfolioInfoViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}