package com.example.productsmvvm.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapplication.Model.Model_FavouriteCity


@Database(entities = arrayOf(Model_FavouriteCity::class) , version = 1)
abstract class AppDatabase: RoomDatabase() {
                //productsDao
    abstract fun getAllFavouriteCity_FromDAO_InAppDatabase(): WeatherDAOInterface

    companion object{
    //    @Volatile
        private var INSTANCE: AppDatabase? = null
        @Synchronized
        fun getAppDatabaseInstance(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                //val db
                val instance = Room.databaseBuilder(
                    context.applicationContext,AppDatabase::class.java,"city_database")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE=instance
                instance
            }
        }
    }

}

