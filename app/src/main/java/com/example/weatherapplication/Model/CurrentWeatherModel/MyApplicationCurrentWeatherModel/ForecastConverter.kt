package com.example.weatherapplication.Model.CurrentWeatherModel.MyApplicationCurrentWeatherModel

import androidx.room.TypeConverter
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Forecast
import com.google.gson.Gson

class ForecastConverter {
    @TypeConverter
    fun fromForecast(forecast: Model_Forecast): String {
        return Gson().toJson(forecast)
    }

    @TypeConverter
    fun toForecast(forecastString: String): Model_Forecast {
        return Gson().fromJson(forecastString, Model_Forecast::class.java)
    }
}
