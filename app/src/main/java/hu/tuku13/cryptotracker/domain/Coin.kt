package hu.tuku13.cryptotracker.domain

data class Coin(
    val id: Int,
    val name: String,
    val symbol: String,
    val price: Double,
    val percent_change_24h : Double,
    val market_cap: Double
)

val testBTC = Coin(
    1,
    "Bitcoin",
    "BTC",
    57000.0,
    2.29,
    1093458467345.0
)

val testETH = Coin(
    1027,
    "Ethereum",
    "ETH",
    4235.1,
    -2.5,
    502096551329.0

)