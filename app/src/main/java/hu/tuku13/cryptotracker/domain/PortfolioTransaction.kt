package hu.tuku13.cryptotracker.domain

import hu.tuku13.cryptotracker.database.DatabasePortfolioTransaction

data class PortfolioTransaction(
    val id: Long,
    val coinId: Int,
    val amount : Double,
    val price: Double,
    val date: Long,
    val isBuyTransaction: Boolean
) {
    fun asDatabaseModel() : DatabasePortfolioTransaction {
        return DatabasePortfolioTransaction(
            id = id,
            coinId = coinId,
            amount = amount,
            price = price,
            date = date,
            isBuyTransaction = isBuyTransaction
        )
    }
}

fun List<PortfolioTransaction>.asDatabaseModel() : List<DatabasePortfolioTransaction> {
    return map {
        it.asDatabaseModel()
    }
}


