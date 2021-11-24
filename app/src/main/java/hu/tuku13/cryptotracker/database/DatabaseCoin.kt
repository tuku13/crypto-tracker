package hu.tuku13.cryptotracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.tuku13.cryptotracker.domain.Coin

@Entity(tableName = "coin")
data class DatabaseCoin(
    @PrimaryKey
    val id: Int,
    val name: String,
    val symbol: String,
    val price: Double,
    val marketCap: Double,
    val cmcRank: Int,
    val totalSupply: Double,
    val platform: String,
    val dateAdded: String,
    val percentChange1h: Double,
    val percentChange24h : Double,
    val percentChange7d: Double,
    val volumeChange24h: Double,
    val volume24h: Double,
    val isFavourite: Boolean
) {
    fun asDomainModel(): Coin {
        return Coin(
            id = id,
            name = name,
            symbol = symbol,
            price = price,
            marketCap = marketCap,
            cmcRank = cmcRank,
            totalSupply = totalSupply,
            platform = platform,
            dateAdded = dateAdded,
            percentChange1h = percentChange1h,
            percentChange24h = percentChange24h,
            percentChange7d = percentChange7d,
            volumeChange24h = volumeChange24h,
            volume24h = volume24h,
            isFavourite = isFavourite
        )
    }
}

fun List<DatabaseCoin>.asDomainModel(): List<Coin> {
    return map {
        it.asDomainModel()
    }
}
