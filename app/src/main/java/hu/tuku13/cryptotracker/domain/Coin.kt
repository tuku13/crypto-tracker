package hu.tuku13.cryptotracker.domain

import android.os.Parcelable
import hu.tuku13.cryptotracker.database.DatabaseCoin
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coin(
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
    val isFavourite: Boolean) : Parcelable {

    fun asDatabaseModel() : DatabaseCoin {
        return DatabaseCoin(
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

fun List<Coin>.asDatabaseModel(): List<DatabaseCoin> {
    return map {
        it.asDatabaseModel()
    }
}

val testBTC = Coin(
    1,
    "Bitcoin",
    "BTC",
    57000.0,
    534535345345.0,
    1,
    1093458467345.0,
    "Bitcoin",
    "",
    1.2,
    7.89,
    12.345,
    546546354.43,
    32432432432423424.04,
    true
)

val testETH = Coin(
    1027,
    "Ethereum",
    "ETH",
    23000.0,
    5345345345.0,
    2,
    10938467345.0,
    "Bitcoin",
    "",
    1.5,
    8.69,
    11.345,
    583444.478,
    4665466534645.323,
    false
)