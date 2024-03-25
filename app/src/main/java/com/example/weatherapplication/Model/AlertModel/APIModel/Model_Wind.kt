package com.example.weatherapplication.Model.AlertModel.APIModel

import com.google.gson.annotations.SerializedName


data class Model_Wind (

  @SerializedName("speed" ) var speed : Double? = null,
  @SerializedName("deg"   ) var deg   : Int?    = null,
  @SerializedName("gust"  ) var gust  : Double? = null

)