package hu.tuku13.cryptotracker.database

import androidx.room.Embedded
import androidx.room.Relation
import hu.tuku13.cryptotracker.domain.Coin

data class DatabaseCoinWithPortfolioRecord(
    @Embedded val coin: Coin,
    @Relation(
        parentColumn = "id",
        entityColumn = "coinId"
    )
    val records: List<DatabasePortfolioRecord>
)
