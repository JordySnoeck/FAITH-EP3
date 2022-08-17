package com.example.myapplication.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
@Entity(tableName = "comment_table")
data class Comment (
    @PrimaryKey(autoGenerate = true)
    val commentId : Int,
    val postId: Int,
    val commentText : String,
    val commentDate : Date,
    val reaction : Boolean,
    val reactedTo: String,
    val userId : Int,
    val username : String
        ) : Parcelable