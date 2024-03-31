package com.example.weatherapplication.Network

import com.example.productsmvvm.Network.WeatherRemoteDataSourceInterface
import com.example.weatherapplication.Model.AlertModel.APIModel.Model_Alert
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Forecast

class FakeWeatherRemoteDataSourceImplementation: WeatherRemoteDataSourceInterface {
    override suspend fun getForecast_OverNetwork_InRDS(
        lat: String,
        lon: String,
        appid: String
    ): Model_Forecast? {
        TODO("Not yet implemented")
    }

    override suspend fun getAlert_OverNetwork_InRDS(
        lat: String,
        lon: String,
        appid: String
    ): Model_Alert? {
        TODO("Not yet implemented")
    }
}