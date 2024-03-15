package com.example.weatherapplication

import com.google.gson.annotations.SerializedName


data class Model_WeatherArrayList (

  @SerializedName("dt"         ) var dt         : Int?               = null,
  @SerializedName("main"       ) var modelMain       : Model_Main?              = Model_Main(),
  @SerializedName("weather"    ) var modelWeather    : ArrayList<Model_Weather> = arrayListOf(),
  @SerializedName("clouds"     ) var modelClouds     : Model_Clouds?            = Model_Clouds(),
  @SerializedName("wind"       ) var modelWind       : Model_Wind?              = Model_Wind(),
  @SerializedName("visibility" ) var visibility : Int?               = null,
  @SerializedName("pop"        ) var pop        : Float?               = null,
  @SerializedName("sys"        ) var modelSys        : Model_Sys?               = Model_Sys(),
  @SerializedName("dt_txt"     ) var dtTxt      : String?            = null

)