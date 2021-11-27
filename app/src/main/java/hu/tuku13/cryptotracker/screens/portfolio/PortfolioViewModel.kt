package hu.tuku13.cryptotracker.screens.portfolio

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import hu.tuku13.cryptotracker.database.getDatabase
import hu.tuku13.cryptotracker.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PortfolioViewModel(
    private val application: Application
) : ViewModel() {
    private val coinRepository = CoinRepository(getDatabase(application), application)
    val portfolioRecords = coinRepository.portfolioTransactions

    init {
        viewModelScope.launch(Dispatchers.IO){
            coinRepository.loadPortfolio();
        }
    }

    class Factory(
        private val application: Application
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(PortfolioViewModel::class.java)) {
                return PortfolioViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}