package com.example.weatherapplication.Network

import com.example.weatherapplication.Model.AlertModel.APIModel.Model_Alert
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Forecast
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity

sealed class ApiState {

    class Success_ModelForecast_InApiState(val data: List<Model_Forecast>): ApiState()
    class Failure(val msg: Throwable): ApiState()
    object Loading: ApiState()

    class Success_ModelAlert_InApiState(val data: List<Model_Alert>): ApiState()


}