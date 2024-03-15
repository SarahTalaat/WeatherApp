package com.example.productsmvvm.Model

import com.example.weatherapplication.Model.Model_Forecast
import com.example.weatherapplication.Model_City
import com.example.weatherapplication.Model_WeatherList

data class WeatherResponse(var cod:String, var message: Int , var cnt: Int, var list: ArrayList<Model_WeatherList>, var city: Model_City )
    //lateinit var products: Model_Forecast


