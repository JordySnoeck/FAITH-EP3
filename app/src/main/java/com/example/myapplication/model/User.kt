package com.example.myapplication.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_table")
class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val email: String,
    val profilePhoto: Bitmap,
    val firstName: String,
    val lastName: String,
    val age: Int
        ): Parcelable