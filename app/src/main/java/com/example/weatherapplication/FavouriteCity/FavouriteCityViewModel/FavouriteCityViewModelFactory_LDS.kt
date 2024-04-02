package com.example.productsmvvm.FavouriteProducts.FavouriteProductsViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.favouriteCitymvvm.FavouriteCity.FavouriteCityViewModel.FavouriteCityViewModel
import com.example.weatherapplication.Repository.WeatherRepositoryInterface


//1.Send repository
//2.Implement the factory
class FavouriteCityViewModelFactory_LDS(private val  weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteWeatherViewModelFactory: WeatherRepositoryInterface): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavouriteCityViewModel::class.java)){
            FavouriteCityViewModel(weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteWeatherViewModelFactory) as T
        }else{
            throw IllegalArgumentException("FavouriteCity ViewModel class not found")
        }
    }

}

