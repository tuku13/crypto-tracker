package hu.tuku13.cryptotracker.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkStatus(
    val credit_count: Int,
    val elapsed: Int,
    val error_code: Int?,
    val error_message: String? = "",
    val notice: String? = "",
    val timestamp: String,
    val total_count: Int
)