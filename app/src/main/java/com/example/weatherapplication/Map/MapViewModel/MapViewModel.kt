
package com.example.weatherapplication.Map.MapViewModel

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

class MapViewModel(private val weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteWeatherViewModel: WeatherRepositoryInterface): ViewModel() {
                //_product
    private var mapMutableStateFlowList_InMapViewModel: MutableStateFlow<ApiState> = MutableStateFlow<ApiState>(ApiState.Loading)
        //map
    val mapStateFlowList_InMapViewModel: StateFlow<ApiState> = mapMutableStateFlowList_InMapViewModel


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
            weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteWeatherViewModel.getAllStoredFavouriteCity_FromLDS_InWeatherRepository()
                .catch { e ->
                    mapMutableStateFlowList_InMapViewModel.value = ApiState.Failure(e)
                }
                .collect{data ->
                    mapMutableStateFlowList_InMapViewModel.value= ApiState.Success_ModelFavouriteCity_Local_InApiState(data)
                }

        }
    }

}

