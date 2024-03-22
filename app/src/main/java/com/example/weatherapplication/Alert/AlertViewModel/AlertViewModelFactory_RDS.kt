package com.example.weatherapplication.Alert.AlertViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.productsmvvm.Model.WeatherRepositoryInterface

class AlertViewModelFactory_RDS(private val  weatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModelFactory: WeatherRepositoryInterface): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AlertViewModel::class.java)){
            AlertViewModel(weatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModelFactory) as T
        }else{
            throw IllegalArgumentException("Alert ViewModel class not found")
        }
    }
}