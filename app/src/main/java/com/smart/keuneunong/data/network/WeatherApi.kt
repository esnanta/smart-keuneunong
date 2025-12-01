package com.smart.keuneunong.data.network

import com.smart.keuneunong.data.network.model.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/forecast")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = "d62bad4d4dcca7f00bdb8f860dc9819c", // TODO: Move to a secure place
        @Query("units") units: String = "metric"
    ): WeatherData
}

