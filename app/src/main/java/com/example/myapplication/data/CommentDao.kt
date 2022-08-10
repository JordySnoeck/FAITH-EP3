package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.model.Comment


@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addComment(comment: Comment)

    @Update
    fun updateComment(comment: Comment)

    @Delete
    fun deleteComment(comment: Comment)

    @Query("DELETE FROM comment_table")
    fun deleteAllComments()

    @Query("SELECT * FROM comment_table ORDER BY commentId ASC")
    fun readAllComments(): LiveData<List<Comment>>

    @Query("SELECT * FROM comment_table WHERE commentId = :key")
    fun getCommentsFromPost(key : Int): LiveData<List<Comment>>

}