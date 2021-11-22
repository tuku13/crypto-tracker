package hu.tuku13.cryptotracker.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkData(
    val circulating_supply: Double? = -1.0,
    val cmc_rank: Int,
    val date_added: String,
    val id: Int,
    val last_updated: String,
    val max_supply: Double? = -1.0,
    val name: String,
    val num_market_pairs: Int,
    val platform: NetworkCoinPlatform? = nullPlatform,
    val quote: NetworkQuote,
    val slug: String,
    val symbol: String,
    val tags: List<String>,
    val total_supply: Double = -1.0
)