package com.example.weatherapplication.Model.CurrentWeatherModel.MyApplicationCurrentWeatherModel

import androidx.room.TypeConverter
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_City
import com.google.gson.Gson

class CityConverter {
    @TypeConverter
    fun fromCity(city: Model_City): String {
        return Gson().toJson(city)
    }

    @TypeConverter
    fun toCity(cityString: String): Model_City {
        return Gson().fromJson(cityString, Model_City::class.java)
    }
}
