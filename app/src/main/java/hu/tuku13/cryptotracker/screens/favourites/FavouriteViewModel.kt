package hu.tuku13.cryptotracker.screens.favourites

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import hu.tuku13.cryptotracker.database.getDatabase
import hu.tuku13.cryptotracker.domain.Coin
import hu.tuku13.cryptotracker.repository.CoinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteViewModel(
    private val application: Application
) : ViewModel() {
    private val coinRepository = CoinRepository(getDatabase(application), application)
    val favouriteCoins = coinRepository.favouriteCoins

    val isEmpty = Transformations.map(favouriteCoins) {
        it.isEmpty()
    }

    private val _navigateToCoinDetails = MutableLiveData<Coin?>()
    val navigateToCoinDetails: LiveData<Coin?>
        get() = _navigateToCoinDetails

    fun selectCoin(coin: Coin) {
        _navigateToCoinDetails.value = coin
    }

    fun doneNavigating() {
        _navigateToCoinDetails.value = null
    }

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                coinRepository.loadCoins(210)
                coinRepository.loadFavouriteCoins()
            } catch (e: Exception) {
                Log.d("NETWORK INFO", "Overview VM $e")
            }
        }
    }



    class Factory(
        private val application: Application
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
                return FavouriteViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}