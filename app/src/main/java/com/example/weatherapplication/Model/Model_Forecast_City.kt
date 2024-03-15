package com.example.weatherapplication.Model

import com.example.weatherapplication.Model_City
import com.google.gson.annotations.SerializedName

data class Model_Forecast_City(@SerializedName("city") var modelCity: Model_City?)
