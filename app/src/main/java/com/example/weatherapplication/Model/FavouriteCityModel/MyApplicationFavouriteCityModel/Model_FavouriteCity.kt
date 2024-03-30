package com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel

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

    /*
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Model_FavouriteCity

        if (latitude != other.latitude) return false
        if (longitude != other.longitude) return false
        if (cityName != other.cityName) return false
        return id == other.id
    }
     */


    // In Model_FavouriteCity.kt
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Model_FavouriteCity

        if (latitude != other.latitude) return false
        if (longitude != other.longitude) return false
        if (cityName != other.cityName) return false
        return true
    }

    override fun hashCode(): Int {
        var result = latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        result = 31 * result + cityName.hashCode()
        result = 31 * result + id
        return result
    }
}
