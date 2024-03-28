package com.example.weatherapplication.Repository

//import com.example.productsmvvm.Database.WeatherLocalDataSourceInterface
import android.util.Log
import com.example.productsmvvm.Database.WeatherLocalDataSourceInterface
import com.example.productsmvvm.Network.WeatherRemoteDataSourceInterface
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Forecast
import com.example.weatherapplication.Model.AlertModel.APIModel.Model_Alert
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//Singleton
class WeatherRepositoryImplementation private constructor(
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
                Log.i("TAG", "getProductsRepositoryImplementationInstance: " + instance)
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

    /*
        override suspend fun getCod_FromRDS_InWeatherRepository(
            lat: String,
            lon: String,
            appid: String
        ): String? {
            Log.i("TAG", "getCod_FromRDS_InProductsRepository: "+weatherRemoteDataSourceInterface_Instance.getCod_OverNetwork_InRDS(lat, lon, appid))
            return weatherRemoteDataSourceInterface_Instance.getCod_OverNetwork_InRDS(lat, lon, appid)
        }

        override suspend fun getMessage_FromRDS_InWeatherRepository(
            lat: String,
            lon: String,
            appid: String
        ): Int? {
            Log.i("TAG", "getMessage_FromRDS_InProductsRepository: "+ weatherRemoteDataSourceInterface_Instance.getMessage_OverNetwork_InRDS(lat, lon, appid))
            return weatherRemoteDataSourceInterface_Instance.getMessage_OverNetwork_InRDS(lat, lon, appid)
        }

        override suspend fun getCnt_FromRDS_InWeatherRepository(
            lat: String,
            lon: String,
            appid: String
        ): Int? {
            Log.i("TAG", "getCnt_FromRDS_InProductsRepository: "+ weatherRemoteDataSourceInterface_Instance.getCnt_OverNetwork_InRDS(lat, lon, appid))
            return  weatherRemoteDataSourceInterface_Instance.getCnt_OverNetwork_InRDS(lat, lon, appid)
        }

        override suspend fun getList_FromRDS_InWeatherRepository(
            lat: String,
            lon: String,
            appid: String
        ): ArrayList<Model_WeatherArrayList> {
            Log.i("TAG", "getList_FromRDS_InProductsRepository: "+ weatherRemoteDataSourceInterface_Instance.getList_OverNetwork_InRDS(lat, lon, appid))
            return  weatherRemoteDataSourceInterface_Instance.getList_OverNetwork_InRDS(lat, lon, appid)
        }

        override suspend fun getCity_FromRDS_InWeatherRepository(
            city: String,
            appid: String
        ): Model_City? {
            Log.i("TAG", "getCity_FromRDS_InProductsRepository: " + weatherRemoteDataSourceInterface_Instance.getCity_OverNetwork_InRDS(city, appid))
            return weatherRemoteDataSourceInterface_Instance.getCity_OverNetwork_InRDS(city, appid)
        }
    */
    override suspend fun getForecast_FromRDS_InWeatherRepository(
        lat: String,
        lon: String,
        appid: String
    ): Flow<Model_Forecast?> {
        Log.i("TAG", "getForecast_FromRDS_InProductsRepository: "+  weatherRemoteDataSourceInterface_Instance.getForecast_OverNetwork_InRDS(lat, lon, appid))
       // return  weatherRemoteDataSourceInterface_Instance.getForecast_OverNetwork_InRDS(lat, lon, appid)
        val respons = weatherRemoteDataSourceInterface_Instance.getForecast_OverNetwork_InRDS(lat, lon, appid)
        return flow {
            emit(respons)
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

    override suspend fun insertModelTime_FromLDS_InWeatherRepository(time: Model_Time) {
        return weatherLocalDataSourceInterface_Instance.insertModelTimeIntoDatabase_InLDS(time)
    }

    override suspend fun deleteModelTime_FromLDS_InWeatherRepository(time: Model_Time) {
        return weatherLocalDataSourceInterface_Instance.deleteModelTimeFromDatabase_InLDS(time)

    }


    /*
        override suspend fun getAllProducts_FromRDS_InProductsRepository(): List<Products> {
            return weatherRemoteDataSourceInterface_Instance.getAllProductsOverNetwork_InRDS()
        }

        override suspend fun getAllStoredProducts_FromLDS_InProductsRepository(): Flow<List<Products>> {
            return productsLocalDataSourceInterface_Instance.getAllStoredProductsFromDatabase_InLDS()
        }

        override suspend fun insertProduct_FromLDS_InProductsRepository(product: Products) {
            return productsLocalDataSourceInterface_Instance.insertProductIntoDatabase_InLDS(product)
        }

        override suspend fun deleteProduct_FromLDS_InProductsRepository(product: Products) {
            return productsLocalDataSourceInterface_Instance.deleteProductFromDatabase_InLDS(product)
        }
    */

}