package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.model.Comment
import com.example.myapplication.model.Post

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPost(post: Post)

    @Update
    fun updatePost(post: Post)

    @Delete
    fun deletePost(post: Post)

    @Query("DELETE FROM post_table")
    fun deleteAllPosts()


    @Query("SELECT * FROM post_table ORDER BY id ASC")
    fun readAllPosts(): LiveData<List<Post>>

}