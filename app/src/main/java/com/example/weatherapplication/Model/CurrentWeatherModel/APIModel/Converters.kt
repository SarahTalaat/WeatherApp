package com.example.weatherapplication.Model.CurrentWeatherModel.APIModel

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromString(value: String): ArrayList<Model_WeatherArrayList> {
        val listType = object : TypeToken<ArrayList<Model_WeatherArrayList>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Model_WeatherArrayList>): String {
        return Gson().toJson(list)
    }
}
