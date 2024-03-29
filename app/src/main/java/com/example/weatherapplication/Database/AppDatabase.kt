package com.example.productsmvvm.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherapplication.Database.ModelTimeDAOInterface
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity


@Database(entities = arrayOf(Model_FavouriteCity::class , Model_Time::class) , version = 5)
abstract class AppDatabase: RoomDatabase() {
                //productsDao
    abstract fun getAllFavouriteCity_FromDAO_InAppDatabase(): WeatherDAOInterface
    abstract fun getAllModelTime_FromDAO_InAppDatabase(): ModelTimeDAOInterface

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

