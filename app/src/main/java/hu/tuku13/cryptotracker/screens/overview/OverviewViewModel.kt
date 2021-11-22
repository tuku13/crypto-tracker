package hu.tuku13.cryptotracker.screens.overview

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import hu.tuku13.cryptotracker.database.CoinDao
import hu.tuku13.cryptotracker.database.getDatabase
import hu.tuku13.cryptotracker.network.CoinApi
import hu.tuku13.cryptotracker.network.CoinApiResponse
import hu.tuku13.cryptotracker.repository.CoinRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class OverviewViewModel(
    val application: Application
)  : ViewModel(){
    private val coinRepository = CoinRepository(getDatabase(application))
    val coins = coinRepository.coins

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
//        CoinApi.retrofitService.getCoins(10).enqueue(
//            object: Callback<String> {
//                override fun onResponse(call: Call<String>, response: Response<String>) {
//                    _response.value = response.body()
//                    Log.d("Valasz", getResponse.value.toString())
//                }
//
//                override fun onFailure(call: Call<String>, t: Throwable) {
//                    _response.value = "Failure: " + t.message
//                }
//            })
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