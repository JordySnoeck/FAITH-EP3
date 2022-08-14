package com.example.myapplication.data

import com.example.myapplication.model.Client
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @GET("/api/v2/users?fields=user_id%2Cnickname%2Cemail%2Cpicture")
    suspend fun getUser(): Response<List<Client>>

    @GET("/api/v2/users-by-email?fields=user_id%2Cnickname%2Cemail%2Cpicture")
    suspend fun getUserByEmail(@Query("email") email: String):Response<List<Client>>

}