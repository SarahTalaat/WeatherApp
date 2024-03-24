package com.example.weatherapplication.Model.CurrentWeatherModel.APIModel

import com.google.gson.annotations.SerializedName


data class Model_Coord (

  @SerializedName("lat" ) var lat : Double? = null,
  @SerializedName("lon" ) var lon : Double? = null

)