package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Client
import com.example.myapplication.model.Post
import com.example.myapplication.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class MainViewModel (private val repository: Repository) : ViewModel() {

    var myResponse: MutableLiveData<Response<List<Client>>> = MutableLiveData()
    var myResponse2: MutableLiveData<Response<List<Client>>> = MutableLiveData()

    fun getUser(){
        viewModelScope.launch {
            val response:Response<List<Client>> = repository.getUser()
            myResponse.value = response
        }
    }

    fun getUserByEmail(email: String){
        viewModelScope.launch {
            val response:Response<List<Client>> = repository.getUserByEmail(email)
            myResponse2.value = response
        }
    }

}