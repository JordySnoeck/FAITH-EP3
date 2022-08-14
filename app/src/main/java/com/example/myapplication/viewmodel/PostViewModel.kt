package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.MainDatabase
import com.example.myapplication.model.Post
import com.example.myapplication.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel(application: Application): AndroidViewModel(application) {

        val db = MainDatabase.getDatabase(application.applicationContext)

        private val pRepository = PostRepository(db.postDao)

        val posts = pRepository.readAllData


    fun addPost(post: Post){
        viewModelScope.launch(Dispatchers.IO) {
            pRepository.addPost(post)
        }

    }

    fun updatePost(post: Post){
        viewModelScope.launch (Dispatchers.IO){
            pRepository.updatePost(post)
        }
    }

    fun deletePost(post: Post){
        viewModelScope.launch(Dispatchers.IO) {
            pRepository.deletePost(post)
        }
    }


    fun deleteAllPosts(){
        viewModelScope.launch(Dispatchers.IO) {
            pRepository.deleteAllPosts()
        }
    }

    fun getPostsByEmail(email: String): LiveData<List<Post>> {
        return pRepository.getPostsByEmail(email)
    }
}