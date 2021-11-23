package hu.tuku13.cryptotracker.screens.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hu.tuku13.cryptotracker.domain.Coin

class DetailsViewModel(coin: Coin) : ViewModel(){
    private val _selectedCoin = MutableLiveData<Coin>()
    val selectedCoin: LiveData<Coin>
        get() = _selectedCoin

    init {
        _selectedCoin.value = coin
    }

    class Factory(private val coin: Coin) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
                return DetailsViewModel(coin) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}