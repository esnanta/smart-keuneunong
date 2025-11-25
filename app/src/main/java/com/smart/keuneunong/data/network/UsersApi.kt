package com.smart.keuneunong.data.network

import com.smart.keuneunong.data.network.model.UserApiModel
import retrofit2.http.GET

interface UsersApi {

    @GET("/repos/square/retrofit/stargazers")
    suspend fun getUsers(): List<UserApiModel>
}