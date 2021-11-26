package hu.tuku13.cryptotracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "portfolio_record")
data class DatabasePortfolioRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val coinId: Int,
    val amount : Double,
    val price: Double,
    val date: Long
)
