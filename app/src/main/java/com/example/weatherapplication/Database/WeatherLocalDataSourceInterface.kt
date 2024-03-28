package com.example.productsmvvm.Database


import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity
import kotlinx.coroutines.flow.Flow

interface WeatherLocalDataSourceInterface {

    suspend fun insertFavouriteCityIntoDatabase_InLDS(city: Model_FavouriteCity)
    suspend fun deleteFavouriteCityFromDatabase_InLDS(city: Model_FavouriteCity)
    suspend fun getAllStoredFavouriteCityFromDatabase_InLDS() : Flow<List<Model_FavouriteCity>>

    suspend fun insertModelTimeIntoDatabase_InLDS(lat:String="",
                                                  lon:String="",
                                                  startDate: String ="",
                                                  endDate: String ="",
                                                  specificTime: String="",
                                                  city: String="")
    suspend fun deleteModelTimeFromDatabase_InLDS(lat:String="",
                                                  lon:String="",
                                                  startDate: String ="",
                                                  endDate: String ="",
                                                  specificTime: String="",
                                                  city: String="")
    suspend fun getAllStoredModelTimeFromDatabase_InLDS() : Flow<List<Model_Time>>

}

