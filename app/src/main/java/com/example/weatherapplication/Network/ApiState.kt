package com.example.weatherapplication.Network

import com.example.weatherapplication.Model.AlertModel.APIModel.Model_Alert
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Forecast
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity

sealed class ApiState {

    class Success_ModelForecast_Remote_InApiState(val data: Model_Forecast?): ApiState()
    class Failure(val msg: Throwable): ApiState()
    object Loading: ApiState()

    class Success_ModelAlert_Remote_InApiState(val data: Model_Alert?): ApiState()

    class Success_ModelTime_Local_InApiState(val data: Model_Time): ApiState()
    class Success_ModelFavouriteCity_Local_InApiState(val data: Model_FavouriteCity): ApiState()

}