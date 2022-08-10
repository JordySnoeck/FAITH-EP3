package com.example.myapplication.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.model.User
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "post_table")
data class Post (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: Date,
    val user: String,
    val postText: String,
    val postName: String,
        ) : Parcelable