package hu.tuku13.cryptotracker.screens.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavouriteViewModel(

) : ViewModel() {

    class Factory(

    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
                return FavouriteViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}