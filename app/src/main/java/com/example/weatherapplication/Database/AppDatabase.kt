package com.example.productsmvvm.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.productsmvvm.Model.Products


@Database(entities = arrayOf(Products::class) , version = 1)
abstract class AppDatabase: RoomDatabase() {
                //productsDao
    abstract fun getAllProducts_FromDAO_InAppDatabase(): ProductsDAOInterface

    companion object{
    //    @Volatile
        private var INSTANCE: AppDatabase? = null
        @Synchronized
        fun getAppDatabaseInstance(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                //val db
                val instance = Room.databaseBuilder(
                    context.applicationContext,AppDatabase::class.java,"products_database")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE=instance
                instance
            }
        }
    }

}