package com.example.productsmvvm.AllProducts.AllProductsViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.productsmvvm.Model.WeatherRepositoryInterface

class CurrentWeatherViewModelFactory_RDS(private val  productsRepositoryInterface_Instance_ConstructorParameter_InAllWeatherViewModelFactory: WeatherRepositoryInterface): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CurrentWeatherViewModel::class.java)){
            CurrentWeatherViewModel(productsRepositoryInterface_Instance_ConstructorParameter_InAllWeatherViewModelFactory) as T
        }else{
            throw IllegalArgumentException("AllProducts ViewModel class not found")
        }
    }
}