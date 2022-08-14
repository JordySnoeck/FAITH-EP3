package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.model.Post
import com.example.myapplication.data.PostDao
import com.example.myapplication.model.User

class PostRepository(private val postDao: PostDao) {

    val readAllData: LiveData<List<Post>> = postDao.readAllPosts()

    suspend fun addPost(post: Post){
        postDao.addPost(post)
    }

    suspend fun updatePost(post: Post){
        postDao.updatePost(post)
    }

    suspend fun deletePost(post: Post){
        postDao.deletePost(post)
    }

    suspend fun deleteAllPosts() {
        postDao.deleteAllPosts()
    }

    fun getPostsByEmail(email: String): LiveData<List<Post>> {
        return postDao.readPostFromUser(email)
    }

}