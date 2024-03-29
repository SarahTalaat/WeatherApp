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
        return id == other.id
    }

}