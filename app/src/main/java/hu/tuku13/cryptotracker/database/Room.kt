package hu.tuku13.cryptotracker.database

import android.content.Context
import androidx.room.*

@Dao
interface CoinDao {
    @Query("SELECT * FROM coin ORDER BY cmcRank")
    fun getCoins(): List<DatabaseCoin>

    @Query("SELECT * FROM coin WHERE id = :id")
    fun getCoinById(id: Int): DatabaseCoin

    @Query("SELECT * FROM coin WHERE isFavourite")
    fun getFavouriteCoins(): List<DatabaseCoin>

    @Query("SELECT * FROM portfolio_record")
    fun getPortfolioRecords(): List<DatabasePortfolioRecord>

    @Transaction
    @Query("SELECT * FROM coin WHERE id = :coinId")
    fun getCoinAndPortfolioRecordWithCoinId(coinId: Int):
            List<DatabaseCoinWithPortfolioRecord>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCoinsWithoutReplace(coins: List<DatabaseCoin>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoinsWithReplace(coins: List<DatabaseCoin>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrReplaceCoin(coin: DatabaseCoin)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPortfolioRecord(record: DatabasePortfolioRecord)

    @Update
    fun updateCoin(coin: DatabaseCoin)

    @Delete()
    fun removeCoin(coin: DatabaseCoin)
}

@Database(entities = [DatabaseCoin::class, DatabasePortfolioRecord::class], version = 1)
abstract class CoinDatabase : RoomDatabase() {
    abstract val coinDao: CoinDao
}

private lateinit var INSTANCE : CoinDatabase

fun getDatabase(context: Context): CoinDatabase {
    synchronized(CoinDatabase::class.java) {
        if(!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
            CoinDatabase::class.java,
            "coins").build()
        }
    }
    return INSTANCE
}