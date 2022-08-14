package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.MainDatabase
import com.example.myapplication.model.Client
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel(application: Application): AndroidViewModel(application){


    val db = MainDatabase.getDatabase(application.applicationContext)

    private val currentDictionaryId = MutableLiveData<Long>()

    private val uRepository = UserRepository(db.userDao)

    val users = uRepository.readAllData


    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            uRepository.addUser(user)
        }

    }

    fun updateUser(user: User){
        viewModelScope.launch (Dispatchers.IO){
            uRepository.updateUser(user)
        }
    }

    fun deleteUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            uRepository.deleteUser(user)
        }
    }


    fun deleteAllUser(){
        viewModelScope.launch(Dispatchers.IO) {
            uRepository.deleteAllUser()
        }
    }

    fun getUserByEmail(email: String): LiveData<User> {
            return uRepository.getUserByEmail(email)
    }
}