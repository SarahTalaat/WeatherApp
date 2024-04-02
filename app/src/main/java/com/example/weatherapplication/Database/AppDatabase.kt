package com.example.productsmvvm.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherapplication.Database.CurrentWeatherDAOInterfaces
import com.example.weatherapplication.Database.ModelTimeDAOInterface
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import com.example.weatherapplication.Model.CurrentWeatherModel.MyApplicationCurrentWeatherModel.CityConverter
import com.example.weatherapplication.Model.CurrentWeatherModel.MyApplicationCurrentWeatherModel.Converters
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Forecast
import com.example.weatherapplication.Model.CurrentWeatherModel.MyApplicationCurrentWeatherModel.Model_Forecast_Database
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity


@Database(entities = arrayOf(Model_FavouriteCity::class , Model_Time::class , Model_Forecast_Database::class) , version = 7)
@TypeConverters(Converters::class , CityConverter::class)
abstract class AppDatabase: RoomDatabase() {
                //productsDao
    abstract fun getAllFavouriteCity_FromDAO_InAppDatabase(): WeatherDAOInterface
    abstract fun getAllModelTime_FromDAO_InAppDatabase(): ModelTimeDAOInterface
    abstract fun getAllModelForecast_FromDAO_InAppDatabase(): CurrentWeatherDAOInterfaces



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

