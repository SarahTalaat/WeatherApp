package com.example.productsmvvm.Model

//import com.example.productsmvvm.Database.WeatherLocalDataSourceInterface
import com.example.productsmvvm.Network.WeatherRemoteDataSourceInterface
import com.example.weatherapplication.Model_City
import com.example.weatherapplication.Model_WeatherList

//Singleton
class WeatherRepositoryImplementation private constructor(
    private var weatherRemoteDataSourceInterface_Instance: WeatherRemoteDataSourceInterface,
   // private var weatherLocalDataSourceInterface_Instance: WeatherLocalDataSourceInterface
) : WeatherRepositoryInterface
{
    companion object{
        private var instance: WeatherRepositoryImplementation? = null
        fun getProductsRepositoryImplementationInstance(
            weatherRemoteDataSourceInterface: WeatherRemoteDataSourceInterface,
       //     weatherLocalDataSourceInterface: WeatherLocalDataSourceInterface
        ): WeatherRepositoryImplementation{
            return instance?: synchronized(this){
                val temp = WeatherRepositoryImplementation(
                    weatherRemoteDataSourceInterface,
                   // weatherLocalDataSourceInterface
                )
                instance = temp
                temp
            }
        }
    }

    override suspend fun getCod_FromRDS_InProductsRepository(
        lat: String,
        lon: String,
        appid: String
    ): String? {
        return weatherRemoteDataSourceInterface_Instance.getCod_OverNetwork_InRDS(lat, lon, appid)
    }

    override suspend fun getMessage_FromRDS_InProductsRepository(
        lat: String,
        lon: String,
        appid: String
    ): Int? {
        return weatherRemoteDataSourceInterface_Instance.getMessage_OverNetwork_InRDS(lat, lon, appid)
    }

    override suspend fun getCnt_FromRDS_InProductsRepository(
        lat: String,
        lon: String,
        appid: String
    ): Int? {
        return  weatherRemoteDataSourceInterface_Instance.getCnt_OverNetwork_InRDS(lat, lon, appid)
    }

    override suspend fun getList_FromRDS_InProductsRepository(
        lat: String,
        lon: String,
        appid: String
    ): ArrayList<Model_WeatherList> {
        return  weatherRemoteDataSourceInterface_Instance.getList_OverNetwork_InRDS(lat, lon, appid)
    }

    override suspend fun getCity_FromRDS_InProductsRepository(
        city: String,
        appid: String
    ): Model_City? {
        return weatherRemoteDataSourceInterface_Instance.getCity_OverNetwork_InRDS(city, appid)
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