package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.data.UserDao
import com.example.myapplication.model.User

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

    suspend fun deleteAllUser() {
        userDao.deleteAllUsers()
    }

    fun getUserByEmail(email: String): LiveData<User> {
        return userDao.getUserByEmail(email)
    }
}