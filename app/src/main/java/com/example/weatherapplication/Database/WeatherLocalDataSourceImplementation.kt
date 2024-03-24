package com.example.productsmvvm.Database


import android.content.Context
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_FavouriteCity
import kotlinx.coroutines.flow.Flow

class WeatherLocalDataSourceImplementation (context: Context) : WeatherLocalDataSourceInterface{
//dao
    private val weatherDAOInterface_InLDSImp: WeatherDAOInterface by lazy {
        val db: AppDatabase = AppDatabase.getAppDatabaseInstance(context)
        db.getAllFavouriteCity_FromDAO_InAppDatabase()
    }

    override suspend fun insertFavouriteCityIntoDatabase_InLDS(city: Model_FavouriteCity) {
       //dao
        weatherDAOInterface_InLDSImp.insertFavouriteCity_InDAOInterface(city)
    }

    override suspend fun deleteFavouriteCityFromDatabase_InLDS(city: Model_FavouriteCity) {
        //dao
        weatherDAOInterface_InLDSImp.deleteFavouriteCity_InDAOInterface(city)
    }

    override suspend fun getAllStoredFavouriteCityFromDatabase_InLDS(): Flow<List<Model_FavouriteCity>> {
        return weatherDAOInterface_InLDSImp.getAllStoredFavouriteCity_InDAOInterface()
    }


}

 