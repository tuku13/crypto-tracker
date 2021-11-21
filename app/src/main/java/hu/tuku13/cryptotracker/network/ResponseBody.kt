package hu.tuku13.cryptotracker.network

data class ResponseBody(
    val `data`: List<Data>,
    val status: Status
)