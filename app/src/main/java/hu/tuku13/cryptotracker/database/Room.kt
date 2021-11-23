package hu.tuku13.cryptotracker.database

import android.content.Context
import androidx.room.*

@Dao
interface CoinDao {
    @Query("SELECT * FROM coin ORDER BY cmcRank")
    fun getCoins(): List<DatabaseCoin>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoinsWithReplace(coins: List<DatabaseCoin>)

    @Delete()
    fun removeCoin(coin: DatabaseCoin)
}

@Database(entities = [DatabaseCoin::class], version = 1)
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