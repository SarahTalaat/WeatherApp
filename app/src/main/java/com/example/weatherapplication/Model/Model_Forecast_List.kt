package com.example.weatherapplication.Model

import com.example.weatherapplication.Model_WeatherList
import com.google.gson.annotations.SerializedName

data class Model_Forecast_List(@SerializedName("list") var modelWeatherList: ArrayList<Model_WeatherList>)
