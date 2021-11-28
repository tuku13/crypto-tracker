package hu.tuku13.cryptotracker.screens.portfolio.add_to_portfolio

import android.app.Application
import androidx.lifecycle.*
import hu.tuku13.cryptotracker.database.getDatabase
import hu.tuku13.cryptotracker.domain.Coin
import hu.tuku13.cryptotracker.domain.PortfolioTransaction
import hu.tuku13.cryptotracker.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddToPortfolioViewModel(
    private val coinRepository: CoinRepository,
    val coin: Coin
) : ViewModel() {

    private val _addedToPortfolio = MutableLiveData<Boolean>()
    val addedToPortfolio : LiveData<Boolean>
    get() = _addedToPortfolio

    fun addToPortfolioCompleted() {
        _addedToPortfolio.value = false
    }

    fun addToPortfolio(coin: Coin, amount: Double, price: Double, date: Long, isBuyTransaction: Boolean) {
        val record = PortfolioTransaction(
            id = 0,
            coinId = coin.id,
            amount = amount,
            price = price,
            date = date,
            isBuyTransaction
        )

        viewModelScope.launch(Dispatchers.IO) {
            coinRepository.insertToPortfolio(record)
            _addedToPortfolio.postValue(true)
        }

    }

    init {
        _addedToPortfolio.value = false
    }

    class Factory(
        private val coinRepository: CoinRepository,
        val coin: Coin
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(AddToPortfolioViewModel::class.java)) {
                return AddToPortfolioViewModel(coinRepository, coin) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}