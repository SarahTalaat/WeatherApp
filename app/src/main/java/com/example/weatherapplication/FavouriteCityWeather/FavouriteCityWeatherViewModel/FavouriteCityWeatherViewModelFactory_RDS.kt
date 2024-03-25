package com.example.weatherapplication.FavouriteCityWeather.FavouriteCityWeatherViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.Repository.WeatherRepositoryInterface

class FavouriteCityWeatherViewModelFactory_RDS(private val  weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteCityWeatherViewModelFactory: WeatherRepositoryInterface): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavouriteCityWeatherViewModel::class.java)){
            FavouriteCityWeatherViewModel(weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteCityWeatherViewModelFactory) as T
        }else{
            throw IllegalArgumentException("favourite city Weather ViewModel class not found")
        }
    }
}