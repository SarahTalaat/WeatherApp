package com.example.productsmvvm.AllProducts.AllProductsViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.productsmvvm.Model.WeatherRepositoryInterface

class CurrentWeatherViewModelFactory_RDS(private val  weatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModelFactory: WeatherRepositoryInterface): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CurrentWeatherViewModel::class.java)){
            CurrentWeatherViewModel(weatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModelFactory) as T
        }else{
            throw IllegalArgumentException("Current Weater ViewModel class not found")
        }
    }
}