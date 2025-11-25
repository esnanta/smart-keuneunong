package com.smart.keuneunong.data.network

import com.smart.keuneunong.data.network.model.WeatherData
import retrofit2.http.GET

interface WeatherApi {

    @GET("DataMKG/MEWS/DigitalForecast/DigitalForecast-JawaTimur.xml")
    suspend fun getWeather(): WeatherData
}

