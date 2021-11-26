package hu.tuku13.cryptotracker.screens.add_to_portfolio

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hu.tuku13.cryptotracker.database.getDatabase
import hu.tuku13.cryptotracker.domain.Coin
import hu.tuku13.cryptotracker.repository.CoinRepository

class AddToPortfolioViewModel(
    private val application: Application,
    val coin: Coin
) : ViewModel() {
    private val coinRepository = CoinRepository(getDatabase(application))


    class Factory(
        private val application: Application,
        val coin: Coin
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(AddToPortfolioViewModel::class.java)) {
                return AddToPortfolioViewModel(application, coin) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}