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

    @Query("SELECT * FROM post_table WHERE email = :key")
    fun readPostFromUser(key: String): LiveData<List<Post>>

    @Query("SELECT COUNT(id) FROM post_table")
    fun getCount():Int

    @Query("SELECT * FROM post_table  WHERE id = :postId")
    fun readPostWithId(postId: Int?): LiveData<Post>
}