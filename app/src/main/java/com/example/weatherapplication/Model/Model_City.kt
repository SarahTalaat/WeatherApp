package com.example.weatherapplication

import com.google.gson.annotations.SerializedName


data class Model_City (

  @SerializedName("id"         ) var id         : Int?    = null,
  @SerializedName("name"       ) var name       : String? = null,
  @SerializedName("coord"      ) var modelCoord      : Model_Coord?  = Model_Coord(),
  @SerializedName("country"    ) var country    : String? = null,
  @SerializedName("population" ) var population : Int?    = null,
  @SerializedName("timezone"   ) var timezone   : Int?    = null,
  @SerializedName("sunrise"    ) var sunrise    : Int?    = null,
  @SerializedName("sunset"     ) var sunset     : Int?    = null

)