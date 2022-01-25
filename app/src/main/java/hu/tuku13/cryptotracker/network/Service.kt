package hu.tuku13.cryptotracker.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import hu.tuku13.cryptotracker.HEADER_API_KEY
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val BASE_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface CoinApiService {
    @Headers(HEADER_API_KEY)
    @GET("listings/latest")
    suspend fun getCoins(
        @Query("limit") limit: Int,
    ) : CoinApiResponse
}

object CoinApi {
    val retrofitService : CoinApiService by lazy {
        retrofit.create(CoinApiService::class.java)
    }
}

