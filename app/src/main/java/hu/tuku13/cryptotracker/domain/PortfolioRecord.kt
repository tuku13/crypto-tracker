package hu.tuku13.cryptotracker.domain

data class PortfolioRecord(
    val id: Int,
    val coinId: Int,
    val amount : Double,
    val price: Double,
    val date: Long
)
