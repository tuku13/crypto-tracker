package hu.tuku13.cryptotracker.screens.portfolio.buy

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PortfolioBuyViewModel(
    val application: Application
) : ViewModel() {

    class Factory(
        private val application: Application
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(PortfolioBuyViewModel::class.java)) {
                return PortfolioBuyViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}