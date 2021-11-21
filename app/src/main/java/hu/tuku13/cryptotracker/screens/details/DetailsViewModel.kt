package hu.tuku13.cryptotracker.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailsViewModel(

) : ViewModel(){

    class Factory(

    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
                return DetailsViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}