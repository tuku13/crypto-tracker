package hu.tuku13.cryptotracker.screens.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class OverviewViewModel(
    val asd : String
)  : ViewModel(){

    class Factory(
        private val asd: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
                return OverviewViewModel(asd) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}