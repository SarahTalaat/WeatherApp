package com.example.weatherapplication.CurrentWeather.CurrentWeatherView

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.widget.Toast
import java.util.Locale

class GeoUtils(private val context: Context) {

    fun getAddress(latitude: Double?, longitude: Double?): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: List<Address> = geocoder.getFromLocation(latitude!!, longitude!!, 1) as List<Address>
        return if (addresses.isNotEmpty()) {
            addresses[0].getAddressLine(0) ?: ""
        } else {
            "Address not found"
        }
    }

    
    
}