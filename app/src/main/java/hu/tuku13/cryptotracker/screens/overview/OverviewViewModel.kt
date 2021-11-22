package hu.tuku13.cryptotracker.screens.overview

import android.util.Log
import androidx.lifecycle.*
import hu.tuku13.cryptotracker.network.CoinApi
import hu.tuku13.cryptotracker.network.CoinApiResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class OverviewViewModel(
    val asd : String
)  : ViewModel(){

    private val _response = MutableLiveData<CoinApiResponse>()
    val getResponse : LiveData<CoinApiResponse>
        get() = _response

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                _response.value = CoinApi.retrofitService.getCoins(10)
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