package hu.tuku13.cryptotracker.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkCoinPlatform(
    val id: Int,
    val name: String,
    val symbol: String,
    val slug: String,
    val token_address: String
)

val nullPlatform : NetworkCoinPlatform = NetworkCoinPlatform(
    id = -1,
    name = "null",
    symbol = "null",
    slug = "null",
    token_address = "null"
)