package com.example.weatherapplication.Model.CurrentWeatherModel.APIModel

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "city_table")
class Model_FavouriteCity (
    var latitude: String ,
    var longitude: String ,
    var cityName: String,
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0){


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Model_FavouriteCity

        if (latitude != other.latitude) return false
        if (longitude != other.longitude) return false
        return cityName == other.cityName
    }

}
