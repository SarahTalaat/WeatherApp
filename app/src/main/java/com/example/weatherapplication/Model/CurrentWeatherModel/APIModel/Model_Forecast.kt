package com.example.weatherapplication.Model.CurrentWeatherModel.APIModel

import com.google.gson.annotations.SerializedName

data class Model_Forecast(

    @SerializedName("cod") var cod: String? = null,
    @SerializedName("message") var message: Int? = null,
    @SerializedName("cnt") var cnt: Int? = null,
    @SerializedName("list") var modelWeatherArrayList: ArrayList<Model_WeatherArrayList> = arrayListOf(),
    @SerializedName("city") var modelCity: Model_City? = Model_City()

)
