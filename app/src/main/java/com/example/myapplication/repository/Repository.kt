package com.example.myapplication.repository

import android.util.Log
import com.example.myapplication.data.RetrofitInstance
import com.example.myapplication.model.Client
import com.example.myapplication.model.User
import retrofit2.Response

class Repository {

     suspend fun getUser(): Response<List<Client>> {
        return RetrofitInstance.api.getUser()
    }
    suspend fun getUserByEmail(email : String): Response<List<Client>> {
        return RetrofitInstance.api.getUserByEmail(email)
    }

}