package hu.tuku13.cryptotracker.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val BASE_URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/"
private const val HEADER_API_KEY = "X-CMC_PRO_API_KEY: 9ad83e4f-7f92-4bd2-8e9b-79205a560121"

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
    fun getCoins(
        @Query("limit") limit: Int,
        //@Query("CMC_PRO_API_KEY") api_key : String = "9ad83e4f-7f92-4bd2-8e9b-79205a560121"
    ) : Call<String>
}

object CoinApi {
    val retrofitService : CoinApiService by lazy {
        retrofit.create(CoinApiService::class.java)
    }
}

