package com.smart.keuneunong.data.network.model

import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("list")
    val list: List<Forecast> = emptyList()
)

data class Forecast(
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("main")
    val main: Main,
    @SerializedName("weather")
    val weather: List<Weather>
)

data class Main(
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("temp_min")
    val temp_min: Double,
    @SerializedName("temp_max")
    val temp_max: Double
)

data class Weather(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)

