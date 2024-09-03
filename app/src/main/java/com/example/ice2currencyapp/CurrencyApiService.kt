package com.example.ice2currencyapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService {
    @GET("lastest")
    fun getExchangeRates(
        @Query("base") baseCurrency: String,
        @Query("apikey") apiKey: String
    ): Call<CurrencyResponse>
}