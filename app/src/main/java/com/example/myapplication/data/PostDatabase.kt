package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.model.Post


@Database(entities = [Post::class], version = 2 , exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class PostDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao

    companion object{
        @Volatile
        private var INSTANCE: PostDatabase? = null

        //als er een instance is dan gebruiken we die anders maken we een nieuwe

        fun getDatabase(context: Context): PostDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostDatabase::class.java,
                    "post_database"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}