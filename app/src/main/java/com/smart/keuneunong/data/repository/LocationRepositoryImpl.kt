package com.smart.keuneunong.data.repository

import com.smart.keuneunong.data.model.AcehneseCity
import com.smart.keuneunong.domain.repository.LocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor() : LocationRepository {

    private val acehneseCities = listOf(
        AcehneseCity(5.5577, 95.3222, "Banda Aceh"),
        AcehneseCity(5.1801, 97.1507, "Lhokseumawe"),
        AcehneseCity(5.0915, 97.3174, "Langsa"),
        AcehneseCity(4.1721, 96.2524, "Meulaboh"),
        AcehneseCity(3.9670, 97.0253, "Tapaktuan"),
        AcehneseCity(4.3726, 97.7925, "Singkil"),
        AcehneseCity(5.5291, 96.9490, "Bireuen"),
        AcehneseCity(4.5159, 96.4167, "Calang"),
        AcehneseCity(5.1895, 96.7446, "Sigli"),
        AcehneseCity(4.9637, 97.6274, "Idi"),
        AcehneseCity(5.3090, 96.8097, "Lhoksukon"),
        AcehneseCity(4.6951, 96.2493, "Jantho"),
        AcehneseCity(4.3588, 97.1953, "Blangkejeren"),
        AcehneseCity(4.0329, 96.8157, "Kutacane"),
        AcehneseCity(5.0260, 97.4012, "Kuala Simpang"),
        AcehneseCity(4.8401, 97.1534, "Takengon"),
        AcehneseCity(5.2491, 96.5039, "Lhoknga"),
        AcehneseCity(5.4560, 95.6203, "Sabang")
    )

    override fun getCityName(latitude: Double, longitude: Double): String {
        val tolerance = 0.1

        for (city in acehneseCities) {
            val latDiff = kotlin.math.abs(latitude - city.latitude)
            val lngDiff = kotlin.math.abs(longitude - city.longitude)

            if (latDiff <= tolerance && lngDiff <= tolerance) {
                return city.name
            }
        }
        return "Aceh"
    }
}

