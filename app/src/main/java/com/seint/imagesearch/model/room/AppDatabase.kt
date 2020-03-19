package com.seint.imagesearch.model.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.seint.imagesearch.model.ImageModel

@Database(entities = arrayOf(ImageModel::class),version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun imageModelDao() : ImageModelDao

    companion object{
        //singleton to prevent multiple instances of database opening
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDataBase(context: Context) : AppDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return  tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java,"appDataBase").build()
                INSTANCE = instance
                return  instance
            }
        }
    }
}
