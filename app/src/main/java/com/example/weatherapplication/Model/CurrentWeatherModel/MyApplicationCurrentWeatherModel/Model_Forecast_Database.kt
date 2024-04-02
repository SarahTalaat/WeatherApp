package com.example.weatherapplication.Model.CurrentWeatherModel.MyApplicationCurrentWeatherModel

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Forecast


@Entity(tableName = "current_weather_table")
data class Model_Forecast_Database(
    val modelForcast: Model_Forecast,
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0
)
