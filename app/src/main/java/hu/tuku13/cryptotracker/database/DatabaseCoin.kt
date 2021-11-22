package hu.tuku13.cryptotracker.database

import hu.tuku13.cryptotracker.domain.Coin

data class DatabaseCoin(
    val id: Int,
    val name: String,
    val symbol: String,
    val price: Double,
    val marketCap: Double,
    val cmcRank: Int,
    val totalSupply: Double,
    val platform: String, //ha null akkor önmaga lesz a platform
    val dateAdded: String,
    val percentChange1h: Double,
    val percentChange24h : Double,
    val percentChange7d: Double,
    val volumeChange24h: Double
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
            volumeChange24h = volumeChange24h
        )
    }
}
