package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.data.CommentDao
import com.example.myapplication.model.Comment

class CommentRepository(private  val commentDao : CommentDao) {

    val readAllData: LiveData<List<Comment>> = commentDao.readAllComments()

     fun addComment(comment: Comment){
        commentDao.addComment(comment)
    }

    fun getCommentsFromPost(id : Int): LiveData<List<Comment>> {
       return commentDao.getCommentsFromPost(id)
    }

     fun updateComment(comment: Comment){
        commentDao.updateComment(comment)
    }

     fun deleteComment(commentId: Int){
        commentDao.deleteComment(commentId)
    }

     fun deleteAllComments() {
        commentDao.deleteAllComments()
    }
}
