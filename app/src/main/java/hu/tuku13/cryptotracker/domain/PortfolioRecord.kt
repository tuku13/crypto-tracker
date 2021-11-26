package hu.tuku13.cryptotracker.domain

import hu.tuku13.cryptotracker.database.DatabasePortfolioRecord

data class PortfolioRecord(
    val id: Long,
    val coinId: Int,
    val amount : Double,
    val price: Double,
    val date: Long
) {
    fun asDatabaseModel() : DatabasePortfolioRecord {
        return DatabasePortfolioRecord(
            id = id,
            coinId = coinId,
            amount = amount,
            price = price,
            date = date
        )
    }
}

fun List<PortfolioRecord>.asDatabaseModel() : List<DatabasePortfolioRecord> {
    return map {
        it.asDatabaseModel()
    }
}


