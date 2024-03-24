
package com.example.favouriteCitymvvm.FavouriteCity.FavouriteCityViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.Repository.WeatherRepositoryInterface
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_FavouriteCity

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteCityViewModel(private val weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteWeatherViewModel: WeatherRepositoryInterface): ViewModel() {
                //_product
    private var favouriteCityMutableLiveDataList_InFavouriteCityViewModel: MutableLiveData<List<Model_FavouriteCity>> = MutableLiveData<List<Model_FavouriteCity>>()
        //favouriteCity
    val favouriteCityLiveDataList_InFavouriteCityViewModel: LiveData<List<Model_FavouriteCity>> = favouriteCityMutableLiveDataList_InFavouriteCityViewModel


    init {
        getAllLocalFavouriteCity_StoredInDatabase_InFavouriteCityViewModel()
    }

    fun deleteFavouriteCity_InFavouriteCityViewModel(city: Model_FavouriteCity){
        viewModelScope.launch(Dispatchers.IO){
            weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteWeatherViewModel.deleteFavouriteCity_FromLDS_InWeatherRepository(city)
            getAllLocalFavouriteCity_StoredInDatabase_InFavouriteCityViewModel()
        }
    }

    fun insertFavouriteCity_InFavouriteCityViewModel(city: Model_FavouriteCity){
        viewModelScope.launch(Dispatchers.IO){
            weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteWeatherViewModel.insertFavouriteCity_FromLDS_InWeatherRepository(city)
            getAllLocalFavouriteCity_StoredInDatabase_InFavouriteCityViewModel()
        }
    }

    fun getAllLocalFavouriteCity_StoredInDatabase_InFavouriteCityViewModel(){
        viewModelScope.launch(Dispatchers.IO){
            weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteWeatherViewModel.getAllStoredFavouriteCity_FromLDS_InWeatherRepository().collect{ favouriteCity -> favouriteCityMutableLiveDataList_InFavouriteCityViewModel.postValue(favouriteCity)}
        }
    }

}

