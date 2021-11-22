package hu.tuku13.cryptotracker.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.tuku13.cryptotracker.database.CoinDatabase
import hu.tuku13.cryptotracker.database.asDomainModel
import hu.tuku13.cryptotracker.domain.Coin
import hu.tuku13.cryptotracker.domain.asDatabaseModel
import hu.tuku13.cryptotracker.network.CoinApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class CoinRepository(private val database: CoinDatabase) {
    private val _coins = MutableLiveData<List<Coin>>()
    private val USE_INTERNET = false

    val coins : LiveData<List<Coin>>
            get() = _coins

    private suspend fun downloadCoinsFromAPI(limit : Int = 15) {
        try {
            val response = CoinApi.retrofitService.getCoins(limit)
            val coinList = mutableListOf<Coin>()
            response.data.forEach {
                coinList.add(Coin(
                    id = it.id,
                    name = it.name,
                    symbol = it.symbol,
                    price = it.quote.priceData.price,
                    marketCap = it.quote.priceData.market_cap,
                    cmcRank = it.cmc_rank,
                    totalSupply = it.total_supply,
                    platform = it.platform?.name ?: it.name,
                    dateAdded = it.date_added,
                    percentChange1h = it.quote.priceData.percent_change_1h,
                    percentChange24h = it.quote.priceData.percent_change_24h,
                    percentChange7d = it.quote.priceData.percent_change_7d,
                    volumeChange24h = it.quote.priceData.volume_change_24h
                ))
            }
            _coins.postValue(coinList)
            database.coinDao.insertCoinsWithReplace(coinList.asDatabaseModel())
            coins.value?.forEach {
                Log.d("DOWNLOADED_COIN", it.name)
            }

        } catch (e: Exception) {
            Log.d("NETWORK INFO", "Failure")
            Log.d("NETWORK INFO", e.message ?: "")
        }
    }

    suspend fun loadCoins(limit : Int = 15) {
        withContext(Dispatchers.IO) {
            if(USE_INTERNET) {
                downloadCoinsFromAPI(limit)
            } else {
                val databaseCoinList = database.coinDao.getCoins()
                val coinList = databaseCoinList.asDomainModel()
                _coins.postValue(coinList)
            }
        }
    }
}