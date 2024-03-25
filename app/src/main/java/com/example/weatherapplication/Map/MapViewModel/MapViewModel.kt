
package com.example.weatherapplication.Map.MapViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.Repository.WeatherRepositoryInterface
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel(private val weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteWeatherViewModel: WeatherRepositoryInterface): ViewModel() {
                //_product
    private var mapMutableLiveDataList_InMapViewModel: MutableLiveData<List<Model_FavouriteCity>> = MutableLiveData<List<Model_FavouriteCity>>()
        //map
    val mapLiveDataList_InMapViewModel: LiveData<List<Model_FavouriteCity>> = mapMutableLiveDataList_InMapViewModel


    init {
        getAllLocalFavouriteCity_StoredInDatabase_InMapViewModel()
    }

    fun deleteFavouriteCity_InMapViewModel(city: Model_FavouriteCity){
        viewModelScope.launch(Dispatchers.IO){
            weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteWeatherViewModel.deleteFavouriteCity_FromLDS_InWeatherRepository(city)
            getAllLocalFavouriteCity_StoredInDatabase_InMapViewModel()
        }
    }

    fun insertFavouriteCity_InMapViewModel(city: Model_FavouriteCity){
        viewModelScope.launch(Dispatchers.IO){
            weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteWeatherViewModel.insertFavouriteCity_FromLDS_InWeatherRepository(city)
            getAllLocalFavouriteCity_StoredInDatabase_InMapViewModel()
        }
    }

    fun getAllLocalFavouriteCity_StoredInDatabase_InMapViewModel(){
        viewModelScope.launch(Dispatchers.IO){
            weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteWeatherViewModel.getAllStoredFavouriteCity_FromLDS_InWeatherRepository().collect{ map -> mapMutableLiveDataList_InMapViewModel.postValue(map)}
        }
    }

}

