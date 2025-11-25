package com.smart.keuneunong.data.network

import com.smart.keuneunong.data.network.model.DetailsApiModel
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailsApi {

    @GET("/users/{user}")
    suspend fun getDetails(@Path("user") user: String): DetailsApiModel
}