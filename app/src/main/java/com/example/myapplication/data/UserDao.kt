package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Query
import com.example.myapplication.model.User



@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("DELETE FROM user_table")
    fun deleteAllUsers()


    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE email = :key LIMIT 1")
    fun getUserByEmail(key : String): LiveData<User>

}