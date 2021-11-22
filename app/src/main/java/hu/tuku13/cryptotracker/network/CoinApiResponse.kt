package hu.tuku13.cryptotracker.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoinApiResponse(
    val data: List<NetworkData>,
    val status: NetworkStatus
)