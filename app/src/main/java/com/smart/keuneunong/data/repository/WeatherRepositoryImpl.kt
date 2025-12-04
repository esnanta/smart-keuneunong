package com.smart.keuneunong.data.repository

import timber.log.Timber
import com.smart.keuneunong.data.model.WeatherData
import com.smart.keuneunong.data.network.WeatherApi
import com.smart.keuneunong.domain.repository.WeatherRepository
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {

    override suspend fun getWeather(latitude: Double, longitude: Double): WeatherData {
        return try {
            // Try to fetch real weather data from API
            Timber.d("Fetching weather data from API for lat: $latitude, lon: $longitude")
            val apiResponse = weatherApi.getWeather(latitude, longitude)

            // Get current forecast
            val currentForecast = apiResponse.list.firstOrNull()

            if (currentForecast != null) {
                val temp = currentForecast.main.temp.toInt()
                val weatherCondition = currentForecast.weather.firstOrNull()
                val condition = translateCondition(weatherCondition?.main ?: "Clear")

                Timber.i("Weather data fetched successfully from API")
                WeatherData(
                    temperature = temp,
                    condition = condition,
                    humidity = 72, // Mock data - could be extracted from API if available
                    windSpeed = 14, // Mock data - could be extracted from API if available
                    weatherIcon = getWeatherIcon(weatherCondition?.main),
                    feelsLike = temp + 3, // Approximate feels like
                    location = getCityName(latitude, longitude),
                    date = System.currentTimeMillis(),
                    sunrise = "06:18", // Mock data - could be extracted from API if available
                    sunset = "18:45", // Mock data - could be extracted from API if available
                    waveHeightMin = 0.5,
                    waveHeightMax = 1.2,
                    seaWindSpeed = 18,
                    seaWindDirection = "Barat",
                    keuneunongContext = getKeuneunongContext(condition),
                    seaConditionSummary = "Kondisi laut cukup tenang, aman untuk aktivitas melaut. Tetap waspada terhadap perubahan cuaca tiba-tiba."
                )
            } else {
                // If no forecast data, use mock data
                Timber.w("No forecast data available, using mock data")
                getMockWeatherData(latitude, longitude)
            }
        } catch (e: SocketTimeoutException) {
            // Specific handling for timeout
            Timber.e(e, "API request timeout after 10 seconds, falling back to mock data")
            getMockWeatherData(latitude, longitude)
        } catch (e: IOException) {
            // Network error (no internet, connection failed, etc.)
            Timber.e(e, "Network error occurred, falling back to mock data")
            getMockWeatherData(latitude, longitude)
        } catch (e: Exception) {
            // If API call fails for any other reason, fallback to mock data
            Timber.e(e, "Failed to fetch weather data, falling back to mock data")
            getMockWeatherData(latitude, longitude)
        }
    }

    private fun getMockWeatherData(latitude: Double, longitude: Double): WeatherData {
        return WeatherData(
            temperature = 29,
            condition = "Cerah Berawan",
            humidity = 72,
            windSpeed = 14,
            weatherIcon = "☀️",
            feelsLike = 32,
            location = getCityName(latitude, longitude),
            date = System.currentTimeMillis(),
            sunrise = "06:18",
            sunset = "18:45",
            waveHeightMin = 0.5,
            waveHeightMax = 1.2,
            seaWindSpeed = 18,
            seaWindDirection = "Barat",
            keuneunongContext = "Saat ini: Keuneunong Ròt (Musim Tanam)\nCuaca cerah berawan sangat baik untuk memulai pengolahan sawah dan penanaman padi. Waspadai hujan singkat di sore hari.",
            seaConditionSummary = "Kondisi laut cukup tenang, aman untuk aktivitas melaut. Tetap waspada terhadap perubahan cuaca tiba-tiba."
        )
    }

    private fun translateCondition(condition: String): String {
        return when (condition.lowercase()) {
            "clear" -> "Cerah"
            "clouds" -> "Berawan"
            "rain", "drizzle" -> "Hujan"
            "thunderstorm" -> "Badai"
            "snow" -> "Salju"
            "mist", "fog" -> "Berkabut"
            else -> "Cerah Berawan"
        }
    }

    private fun getWeatherIcon(condition: String?): String {
        return when (condition?.lowercase()) {
            "clear" -> "☀️"
            "clouds" -> "☁️"
            "rain", "drizzle" -> "🌧️"
            "thunderstorm" -> "⛈️"
            "snow" -> "❄️"
            "mist", "fog" -> "🌫️"
            else -> "⛅"
        }
    }

    private fun getCityName(latitude: Double, longitude: Double): String {
        // Simple city name based on coordinates
        return when {
            latitude in 5.15..5.20 && longitude in 97.10..97.15 -> "Lhokseumawe"
            latitude in 5.53..5.58 && longitude in 95.30..95.35 -> "Banda Aceh"
            latitude in 3.55..3.60 && longitude in 96.70..96.75 -> "Bireuen"
            else -> "Aceh"
        }
    }

    private fun getKeuneunongContext(condition: String): String {
        return when {
            condition.contains("Hujan") ->
                "Saat ini: Keuneunong Ròt (Musim Tanam)\nCuaca hujan, sebaiknya tunda aktivitas bertani di luar. Cocok untuk merawat tanaman di dalam ruangan atau perencanaan."
            condition.contains("Cerah") ->
                "Saat ini: Keuneunong Ròt (Musim Tanam)\nCuaca cerah sangat baik untuk memulai pengolahan sawah dan penanaman padi. Manfaatkan cuaca baik ini."
            else ->
                "Saat ini: Keuneunong Ròt (Musim Tanam)\nCuaca berawan, masih cocok untuk aktivitas bertani. Waspadai kemungkinan hujan."
        }
    }
}

