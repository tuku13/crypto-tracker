package hu.tuku13.cryptotracker.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.tuku13.cryptotracker.database.CoinDatabase
import hu.tuku13.cryptotracker.database.DatabaseCoin
import hu.tuku13.cryptotracker.database.asDomainModel
import hu.tuku13.cryptotracker.domain.Coin
import hu.tuku13.cryptotracker.domain.PortfolioTransaction
import hu.tuku13.cryptotracker.network.CoinApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class CoinRepository(private val database: CoinDatabase, context: Context) {
    private val _coins = MutableLiveData<List<Coin>>()
    private val sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
    val coins : LiveData<List<Coin>>
            get() = _coins

    private val _favouriteCoins = MutableLiveData<List<Coin>>()
    val favouriteCoins : LiveData<List<Coin>>
        get() = _favouriteCoins

    private val _portfolioTransactions = MutableLiveData<List<PortfolioTransaction>>()
    val portfolioTransactions : LiveData<List<PortfolioTransaction>>
        get() = _portfolioTransactions

    private val _coinWithTransaction = MutableLiveData<HashMap<Coin, MutableList<PortfolioTransaction>>>()
    val coinWithTransaction : LiveData<HashMap<Coin, MutableList<PortfolioTransaction>>>
        get() = _coinWithTransaction

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
                    volumeChange24h = it.quote.priceData.volume_change_24h,
                    volume24h = it.quote.priceData.volume_24h,
                    isFavourite = false
                ))
            }

            coinList.forEach {
                safeInsertCoin(it)
            }

        } catch (e: Exception) {
            Log.d("NETWORK INFO", "CoinRepository $e")
        }
    }

    suspend fun deleteTransaction(transaction: PortfolioTransaction) {
        withContext(Dispatchers.IO) {
            database.coinDao.removeTransaction(transaction.asDatabaseModel())
        }
    }

    suspend fun loadCoinWithTransactions() {
        loadCoins(500)
        val loaded = database.coinDao.getPortfolioTransactions()
        var hashMap = HashMap<Coin, MutableList<PortfolioTransaction>>()
        loaded.forEach {
            val coin = getCoinById(it.coinId)
            var list = hashMap[coin] ?: mutableListOf()
            list.add(it.asDomainModel())
            hashMap[coin] = list
        }
        _coinWithTransaction.postValue(hashMap)
    }

    private fun getCoinById(id: Int) : Coin {
        return _coins.value?.first {
            it.id == id
        }!!
    }

    private fun shouldRefreshCache(): Boolean {
        val now = System.currentTimeMillis()
        val lastUpdate = sharedPreferences.getLong("lastUpdate", 0)
        val difference = now - lastUpdate

        if(difference >= 5 * 60 * 1000) {
            editor.putLong("lastUpdate", now)
            editor.apply()

            return true
        }
        return false
    }

    private fun safeInsertCoin(downloadedCoin:Coin) {
        val oldCoin = database.coinDao.getCoinById(downloadedCoin.id)
        if(oldCoin != null) {
            val newCoin = DatabaseCoin(
                id = oldCoin.id,
                name = oldCoin.name,
                symbol = oldCoin.symbol,
                price = downloadedCoin.price,
                marketCap = downloadedCoin.marketCap,
                cmcRank = downloadedCoin.cmcRank,
                totalSupply = downloadedCoin.totalSupply,
                platform = oldCoin.platform,
                dateAdded = oldCoin.dateAdded,
                percentChange1h = downloadedCoin.percentChange1h,
                percentChange24h = downloadedCoin.percentChange24h,
                percentChange7d = downloadedCoin.percentChange7d,
                volumeChange24h = downloadedCoin.volumeChange24h,
                volume24h = downloadedCoin.volume24h,
                isFavourite = oldCoin.isFavourite
            )
            database.coinDao.insertOrReplaceCoin(newCoin)
        } else {
            database.coinDao.insertOrReplaceCoin(downloadedCoin.asDatabaseModel())
        }
    }

    suspend fun insertToPortfolio(transaction: PortfolioTransaction) {
        database.coinDao.insertPortfolioTransaction(transaction.asDatabaseModel())
    }

    suspend fun loadPortfolio() {
        val databasePortfolio = database.coinDao.getPortfolioTransactions()
        val converted = databasePortfolio.asDomainModel()
        _portfolioTransactions.postValue(converted)
    }

    fun updateCoin(coin: Coin) {
        database.coinDao.updateCoin(coin.asDatabaseModel())
        loadFavouriteCoins()
    }

    private fun loadCoinsFromDatabase() {
        val databaseCoinList = database.coinDao.getCoins()
        val coinList = databaseCoinList.asDomainModel()
        _coins.postValue(coinList)
    }

    fun loadFavouriteCoins() {
        val databaseFavouriteCoinList = database.coinDao.getFavouriteCoins()
        val favouriteCoinList = databaseFavouriteCoinList.asDomainModel()
        _favouriteCoins.postValue(favouriteCoinList)
    }

    suspend fun loadCoins(limit : Int = 15) {
        withContext(Dispatchers.IO) {
            if(shouldRefreshCache()) {
                downloadCoinsFromAPI(limit)
            }
            loadCoinsFromDatabase()
        }
    }
}