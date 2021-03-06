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
    private val coinRepository: CoinRepository
) : ViewModel() {
//    val coinWithPortfolioTransactions = coinRepository.coinWithTransaction

//    init {
//        viewModelScope.launch(Dispatchers.IO){
//            coinRepository.loadCoinWithTransactions()
//        }
//    }

    class Factory(
        private val coinRepository: CoinRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(PortfolioViewModel::class.java)) {
                return PortfolioViewModel(coinRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}