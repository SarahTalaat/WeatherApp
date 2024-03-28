package com.example.productsmvvm.Database


import android.content.Context
import com.example.weatherapplication.Database.ModelTimeDAOInterface
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity
import kotlinx.coroutines.flow.Flow

class WeatherLocalDataSourceImplementation (context: Context) : WeatherLocalDataSourceInterface{
//dao
    private val weatherDAOInterface_InLDSImp: WeatherDAOInterface by lazy {
        val db: AppDatabase = AppDatabase.getAppDatabaseInstance(context)
        db.getAllFavouriteCity_FromDAO_InAppDatabase()
    }

    private val modelTimeDAOInterface_InLDSImp: ModelTimeDAOInterface by lazy {
        val db: AppDatabase = AppDatabase.getAppDatabaseInstance(context)
        db.getAllModelTime_FromDAO_InAppDatabase()
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

    override suspend fun insertModelTimeIntoDatabase_InLDS(lat:String,
                                                           lon:String,
                                                           startDate: String,
                                                           endDate: String,
                                                           specificTime: String,
                                                           city: String) {
        modelTimeDAOInterface_InLDSImp.insertModelTime_InDAOInterface(lat, lon, startDate, endDate, specificTime, city)
    }

    override suspend fun deleteModelTimeFromDatabase_InLDS(lat:String,
                                                           lon:String,
                                                           startDate: String,
                                                           endDate: String,
                                                           specificTime: String,
                                                           city: String) {
        modelTimeDAOInterface_InLDSImp.deleteModelTime_InDAOInterface(lat, lon, startDate, endDate, specificTime, city)
    }

    override suspend fun getAllStoredModelTimeFromDatabase_InLDS(): Flow<List<Model_Time>> {
        return modelTimeDAOInterface_InLDSImp.getAllStoredModelTime_InDAOInterface()
    }


}

 