package hu.tuku13.cryptotracker.screens.overview

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import hu.tuku13.cryptotracker.database.getDatabase
import hu.tuku13.cryptotracker.domain.Coin
import hu.tuku13.cryptotracker.repository.CoinRepository
import kotlinx.coroutines.launch

class OverviewViewModel(
    val application: Application
)  : ViewModel(){
    private val coinRepository = CoinRepository(getDatabase(application))
    val coins = coinRepository.coins

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
        viewModelScope.launch {
            try {
                coinRepository.loadCoins(200)
                Log.d("NETWORK INFO", "Successful")
            } catch (e: Exception) {
                Log.d("NETWORK INFO", "Failure")
                Log.d("NETWORK INFO", e.toString())
            }
        }
    }


    class Factory(
        private val application: Application
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
                return OverviewViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
