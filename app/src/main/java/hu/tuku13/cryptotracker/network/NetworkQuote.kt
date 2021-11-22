package hu.tuku13.cryptotracker.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkQuote(
    @Json(name = "USD") val priceData: NetworkPriceData
)