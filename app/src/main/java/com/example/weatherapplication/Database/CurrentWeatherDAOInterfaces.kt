package com.example.weatherapplication.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Forecast
import kotlinx.coroutines.flow.Flow


@Dao
interface CurrentWeatherDAOInterfaces {

    @Query("SELECT * FROM current_weather_table ")
    fun getAllStoredModelForecast_InDAOInterface(): Flow<List<Model_Forecast>>

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun insertModelForecast_InDAOInterface (modelForecast: Model_Forecast)

    @Query("DELETE FROM current_weather_table")
    suspend fun deleteAllModelForecast_InDAOInterface(): Int

}