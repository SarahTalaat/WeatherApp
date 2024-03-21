package com.example.weatherapplication.Map.MapViewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.favouriteCitymvvm.FavouriteCity.FavouriteCityViewModel.FavouriteCityViewModel
import com.example.productsmvvm.Model.WeatherRepositoryInterface


//1.Send repository
//2.Implement the factory
class MapViewModelFactory_LDS(private val  weatherRepositoryInterface_Instance_ConstructorParameter_InMapViewModelFactory: WeatherRepositoryInterface ): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MapViewModel::class.java)){
            MapViewModel(weatherRepositoryInterface_Instance_ConstructorParameter_InMapViewModelFactory) as T
        }else{
            throw IllegalArgumentException("Map ViewModel class not found")
        }
    }

}

