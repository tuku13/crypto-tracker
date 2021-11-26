package hu.tuku13.cryptotracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.tuku13.cryptotracker.domain.PortfolioTransaction

@Entity(tableName = "portfolio_transaction")
data class DatabasePortfolioTransaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val coinId: Int,
    val amount : Double,
    val price: Double,
    val date: Long,
    val isBuyTransaction: Boolean
) {
    fun asDomainModel() : PortfolioTransaction {
        return PortfolioTransaction(
            id = id,
            coinId = coinId,
            amount = amount,
            price = price,
            date = date,
            isBuyTransaction = isBuyTransaction
        )
    }
}

fun List<DatabasePortfolioTransaction>.asDomainModel() : List<PortfolioTransaction> {
    return map {
        it.asDomainModel()
    }
}
