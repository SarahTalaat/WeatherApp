package com.example.weatherapplication.Repository

//import com.example.productsmvvm.Database.WeatherLocalDataSourceInterface
import android.util.Log
import com.example.productsmvvm.Database.WeatherLocalDataSourceInterface
import com.example.productsmvvm.Network.WeatherRemoteDataSourceInterface
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Forecast
import com.example.weatherapplication.Model.AlertModel.APIModel.Model_Alert
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import com.example.weatherapplication.Model.CurrentWeatherModel.MyApplicationCurrentWeatherModel.Model_Forecast_Database
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//Singleton
class WeatherRepositoryImplementation  constructor(
    private var weatherRemoteDataSourceInterface_Instance: WeatherRemoteDataSourceInterface,
    private var weatherLocalDataSourceInterface_Instance: WeatherLocalDataSourceInterface
) : WeatherRepositoryInterface
{
    companion object{
        private var instance: WeatherRepositoryImplementation? = null
        fun getWeatherRepositoryImplementationInstance(
            weatherRemoteDataSourceInterface: WeatherRemoteDataSourceInterface,
            weatherLocalDataSourceInterface: WeatherLocalDataSourceInterface
        ): WeatherRepositoryImplementation {
            return instance ?: synchronized(this){
                val temp = WeatherRepositoryImplementation(
                    weatherRemoteDataSourceInterface,
                    weatherLocalDataSourceInterface
                )
                instance = temp
               // Log.i("TAG", "getProductsRepositoryImplementationInstance: " + instance)
                temp
            }
        }
    }

    override suspend fun getAlert_FromRDS_InWeatherRepository(
        lat: String,
        lon: String,
        appid: String
    ): Flow<Model_Alert?> {
        Log.i("TAG", "getAlert_FromRDS_InWeatherRepository: " + weatherRemoteDataSourceInterface_Instance.getAlert_OverNetwork_InRDS(lat, lon, appid))

        //return  weatherRemoteDataSourceInterface_Instance.getAlert_OverNetwork_InRDS()

        val respons = weatherRemoteDataSourceInterface_Instance.getAlert_OverNetwork_InRDS(lat, lon, appid)
        return flow {
            emit(respons)
        }

    }

    override suspend fun getForecast_FromRDS_InWeatherRepository(
        lat: String,
        lon: String,
        units: String,
        lang: String,
        appid: String
    ): Flow<Model_Forecast?> {
        Log.i("TAG", "getForecast_FromRDS_InProductsRepository: "+  weatherRemoteDataSourceInterface_Instance.getForecast_OverNetwork_InRDS(lat, lon, units, lang, appid))
       // return  weatherRemoteDataSourceInterface_Instance.getForecast_OverNetwork_InRDS(lat, lon, appid)
        val response = weatherRemoteDataSourceInterface_Instance.getForecast_OverNetwork_InRDS(lat, lon, units, lang, appid)
        return flow {
            emit(response)
        }
    }

    override suspend fun getAllStoredFavouriteCity_FromLDS_InWeatherRepository(): Flow<List<Model_FavouriteCity>> {
        return weatherLocalDataSourceInterface_Instance.getAllStoredFavouriteCityFromDatabase_InLDS()

    }

    override suspend fun insertFavouriteCity_FromLDS_InWeatherRepository(city: Model_FavouriteCity) {
        return weatherLocalDataSourceInterface_Instance.insertFavouriteCityIntoDatabase_InLDS(city)
    }

    override suspend fun deleteFavouriteCity_FromLDS_InWeatherRepository(city: Model_FavouriteCity) {
        return weatherLocalDataSourceInterface_Instance.deleteFavouriteCityFromDatabase_InLDS(city)

    }

    override suspend fun getAllStoredModelTime_FromLDS_InWeatherRepository(): Flow<List<Model_Time>> {
        return weatherLocalDataSourceInterface_Instance.getAllStoredModelTimeFromDatabase_InLDS()
    }

    override suspend fun insertModelTime_FromLDS_InWeatherRepository(modelTime: Model_Time) {
        return weatherLocalDataSourceInterface_Instance.insertModelTimeIntoDatabase_InLDS(modelTime)
    }

    override suspend fun deleteModelTime_FromLDS_InWeatherRepository(modelTime: Model_Time) {
        return weatherLocalDataSourceInterface_Instance.deleteModelTimeFromDatabase_InLDS(modelTime)

    }

    override suspend fun getAllStoredModelForecast_FromLDS_InWeatherRepository(): Flow<List<Model_Forecast_Database>> {
        return weatherLocalDataSourceInterface_Instance.getAllStoredModelForecastFromDatabase_InLDS()
    }

    override suspend fun insertModelForecast_FromLDS_InWeatherRepository(modelForecastDatabase: Model_Forecast_Database) {
       weatherLocalDataSourceInterface_Instance.insertModelForecastIntoDatabase_InLDS(modelForecastDatabase)
    }

    override suspend fun deleteAllModelForecast_FromLDS_InWeatherRepository() {
        weatherLocalDataSourceInterface_Instance.deleteAllModelForecastFromDatabase_InLDS()
    }

}