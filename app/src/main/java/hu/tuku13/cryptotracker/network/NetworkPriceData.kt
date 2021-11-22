package hu.tuku13.cryptotracker.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkPriceData(
    val fully_diluted_market_cap: Double,
    val last_updated: String,
    val market_cap: Double,
    val market_cap_dominance: Double,
    val percent_change_1h: Double,
    val percent_change_24h: Double,
    val percent_change_30d: Double,
    val percent_change_60d: Double,
    val percent_change_7d: Double,
    val percent_change_90d: Double,
    val price: Double,
    val volume_24h: Double,
    val volume_change_24h: Double
)