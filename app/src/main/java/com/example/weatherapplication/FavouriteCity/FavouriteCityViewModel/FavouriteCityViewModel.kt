
package com.example.favouriteCitymvvm.FavouriteCity.FavouriteCityViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.Repository.WeatherRepositoryInterface
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity
import com.example.weatherapplication.Network.ApiState

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavouriteCityViewModel(private val weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteWeatherViewModel: WeatherRepositoryInterface): ViewModel() {
                //_product
    private var favouriteCityMutableStateFlowList_InFavouriteCityViewModel: MutableStateFlow<ApiState> = MutableStateFlow<ApiState>(ApiState.Loading)
        //favouriteCity
    val favouriteCityStateFlowList_InFavouriteCityViewModel: StateFlow<ApiState> = favouriteCityMutableStateFlowList_InFavouriteCityViewModel


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
            weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteWeatherViewModel.
            getAllStoredFavouriteCity_FromLDS_InWeatherRepository()
                .catch { e ->
                    favouriteCityMutableStateFlowList_InFavouriteCityViewModel.value = ApiState.Failure(e)
                }
                .collect{data ->
                    favouriteCityMutableStateFlowList_InFavouriteCityViewModel.value= ApiState.Success_ModelFavouriteCity_Local_InApiState(data)
                }
        }



    }

}

