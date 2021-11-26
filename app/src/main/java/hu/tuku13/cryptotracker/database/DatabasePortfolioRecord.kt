package hu.tuku13.cryptotracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.tuku13.cryptotracker.domain.PortfolioRecord

@Entity(tableName = "portfolio_record")
data class DatabasePortfolioRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val coinId: Int,
    val amount : Double,
    val price: Double,
    val date: Long
) {
    fun asDomainModel() : PortfolioRecord {
        return PortfolioRecord(
            id = id,
            coinId = coinId,
            amount = amount,
            price = price,
            date = date,
        )
    }
}

fun List<DatabasePortfolioRecord>.asDomainModel() : List<PortfolioRecord> {
    return map {
        it.asDomainModel()
    }
}
