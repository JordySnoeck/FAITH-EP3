package com.example.myapplication.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.model.Comment
import com.example.myapplication.model.Post


@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addComment(comment: Comment)

    @Update
    fun updateComment(comment: Comment)

    @Query("DELETE FROM comment_table WHERE commentId = :key")
    fun deleteComment(key: Int)

    @Query("DELETE FROM comment_table")
    fun deleteAllComments()

    @Query("SELECT * FROM comment_table ORDER BY commentId ASC")
    fun readAllComments(): LiveData<List<Comment>>

    @Query("SELECT * FROM comment_table WHERE postId = :key")
    fun getCommentsFromPost(key : Int): LiveData<List<Comment>>

    @Query("SELECT COUNT(commentId) FROM comment_table")
    fun getCount():Int

    @Query("SELECT * FROM comment_table  WHERE commentId = :commentId")
    fun getCommentById(commentId: Int?): LiveData<Comment>

}