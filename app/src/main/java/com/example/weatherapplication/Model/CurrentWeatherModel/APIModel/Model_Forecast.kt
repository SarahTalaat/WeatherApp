package com.example.weatherapplication.Model.CurrentWeatherModel.APIModel

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//current_weather_table
@Entity(tableName = "current_weather_table")
data class Model_Forecast(

    @SerializedName("cod") var cod: String? = null,
    @SerializedName("message") var message: Int? = null,
    @SerializedName("cnt") var cnt: Int? = null,
    @SerializedName("list") var modelWeatherArrayList: ArrayList<Model_WeatherArrayList> = arrayListOf(),
    @PrimaryKey
    @SerializedName("city") var modelCity: Model_City? = Model_City()

)
