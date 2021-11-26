package hu.tuku13.cryptotracker.database

import androidx.room.Embedded
import androidx.room.Relation
import hu.tuku13.cryptotracker.domain.Coin

data class DatabaseCoinWithPortfolioTransactions(
    @Embedded val coin: Coin,
    @Relation(
        parentColumn = "id",
        entityColumn = "coinId"
    )
    val transactions: List<DatabasePortfolioTransaction>
)
