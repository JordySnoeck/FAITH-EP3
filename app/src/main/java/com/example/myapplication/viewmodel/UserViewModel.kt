package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.MainDatabase
import com.example.myapplication.data.UserDatabase
import com.example.myapplication.repository.UserRepository
import com.example.myapplication.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application){


    val db = MainDatabase.getDatabase(application.applicationContext)



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
}