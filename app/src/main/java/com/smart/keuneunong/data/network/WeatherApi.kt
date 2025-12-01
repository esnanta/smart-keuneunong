package com.smart.keuneunong.data.network

import com.smart.keuneunong.BuildConfig
import com.smart.keuneunong.data.network.model.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/forecast")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = BuildConfig.WEATHER_API_KEY,
        @Query("units") units: String = "metric"
    ): WeatherData
}

