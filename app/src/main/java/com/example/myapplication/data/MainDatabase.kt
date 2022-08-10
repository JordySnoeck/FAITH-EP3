package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.model.Comment
import com.example.myapplication.model.Post
import com.example.myapplication.model.User


@Database(entities = [Comment::class, Post::class, User::class], version = 1 , exportSchema = false)
@TypeConverters(DateConverter::class, Converters::class)
abstract class MainDatabase : RoomDatabase() {

    abstract val userDao : UserDao
    abstract val postDao : PostDao
    abstract val commentDao: CommentDao

    companion object{
        @Volatile
        private var INSTANCE: MainDatabase? = null

        //als er een instance is dan gebruiken we die anders maken we een nieuwe

        fun getDatabase(context: Context): MainDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "main_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}