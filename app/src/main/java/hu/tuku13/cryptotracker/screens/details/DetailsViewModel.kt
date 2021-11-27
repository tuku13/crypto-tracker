package hu.tuku13.cryptotracker.screens.details

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import hu.tuku13.cryptotracker.database.getDatabase
import hu.tuku13.cryptotracker.domain.Coin
import hu.tuku13.cryptotracker.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class DetailsViewModel(
    private val application: Application,
    val coin: Coin
) : ViewModel(){
    private val coinRepository = CoinRepository(getDatabase(application), application)

    private val _selectedCoin = MutableLiveData<Coin>()

    init {
        _selectedCoin.value = coin
    }

    val isCoinFavourite = Transformations.map(_selectedCoin) {
        it.isFavourite
    }

    private val _navigateAddToPortfolio = MutableLiveData<Coin?>()
    val navigateAddToPortfolio : LiveData<Coin?>
        get() = _navigateAddToPortfolio

    fun addToPortfolio() {
        _navigateAddToPortfolio.value = _selectedCoin.value
    }

    fun doneNavigating() {
        _navigateAddToPortfolio.value = null
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun toggleFavourite() {
        val observedCoin = _selectedCoin.value
        if(observedCoin != null) {
            observedCoin.isFavourite = !observedCoin.isFavourite
            _selectedCoin.value = observedCoin
            viewModelScope.launch(Dispatchers.IO) {
                coinRepository.updateCoin(observedCoin)
            }
        }
    }

    class Factory(
        private val application: Application,
        private val coin: Coin
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
                return DetailsViewModel(application, coin) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}