package com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alert_table")
class Model_Time (
    var latitude: String = "nullValue",
    var longitude: String = "nullValue",
    var startDate: String = "nullValue",
    var endDate: String = "nullValue",
    var specificTime: String = "nullValue",
    var city: String = "nullValue",
    var shallCardAppear: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0){


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Model_Time

        if (latitude != other.latitude) return false
        if (longitude != other.longitude) return false
        if (startDate != other.startDate) return false
        if (endDate != other.endDate) return false
        if (specificTime != other.specificTime) return false
        if (city != other.city) return false
        if (shallCardAppear != other.shallCardAppear) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        var result = latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        result = 31 * result + startDate.hashCode()
        result = 31 * result + endDate.hashCode()
        result = 31 * result + specificTime.hashCode()
        result = 31 * result + city.hashCode()
        result = 31 * result + shallCardAppear.hashCode()
        result = 31 * result + id
        return result
    }
}