package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.MainDatabase
import com.example.myapplication.model.Comment
import com.example.myapplication.repository.CommentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentViewModel(application: Application): AndroidViewModel(application) {

    val db = MainDatabase.getDatabase(application.applicationContext)



    private val cRepository = CommentRepository(db.commentDao)

    val comments = cRepository.readAllData


    fun addComment(comment: Comment){
        viewModelScope.launch(Dispatchers.IO) {
            cRepository.addComment(comment)
        }

    }

    fun updateComment(comment: Comment){
        viewModelScope.launch (Dispatchers.IO){
            cRepository.updateComment(comment)
        }
    }

    fun deleteComment(commentId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            cRepository.deleteComment(commentId)
        }
    }


    fun deleteAllComments(){
        viewModelScope.launch(Dispatchers.IO) {
            cRepository.deleteAllComments()
        }
    }

    fun getCommentsByPostId(postId: Int): LiveData<List<Comment>> {
        return cRepository.getCommentsFromPost(postId)
    }
}