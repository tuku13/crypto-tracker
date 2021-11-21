package hu.tuku13.cryptotracker.screens.portfolio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PortfolioViewModel(

) : ViewModel() {

    class Factory(

    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(PortfolioViewModel::class.java)) {
                return PortfolioViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}