package com.example.weatherapplication.FavouriteCityWeather.FavouriteCityWeatherViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.productsmvvm.Model.Products
import com.example.productsmvvm.Model.WeatherRepositoryInterface
import com.example.weatherapplication.Model.Model_Forecast
import com.example.weatherapplication.Model_City
import com.example.weatherapplication.Model_WeatherArrayList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteCityWeatherViewModel(private val weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteCityWeatherViewModel: WeatherRepositoryInterface):ViewModel() {

/*
    private var productsMutableLiveDataList_InAllProductsViewModel: MutableLiveData<List<Products>> = MutableLiveData<List<Products>>()
    //products
    val productsLiveDataList_InAllProductsViewModel: LiveData<List<Products>> = productsMutableLiveDataList_InAllProductsViewModel

    init {
        getAllRemoteProducts_FromRetrofit_InAllProductsViewModel()
    }

    fun insertProduct_InAllProductsViewModel(product: Products){
        viewModelScope.launch(Dispatchers.IO){
            productsRepositoryInterfaceInstance_ConstructorParameter_InAllWeatherViewModel.insertProduct_FromLDS_InProductsRepository(product)
            getAllRemoteProducts_FromRetrofit_InAllProductsViewModel()
        }
    }

    fun getAllRemoteProducts_FromRetrofit_InAllProductsViewModel(){
        viewModelScope.launch(Dispatchers.IO){
            productsMutableLiveDataList_InAllProductsViewModel.postValue(productsRepositoryInterfaceInstance_ConstructorParameter_InAllWeatherViewModel.getCurrentWeather_FromRDS_InProductsRepository())
        }
    }

 */

/*
    private var stringMutableLiveData_InFavouriteCityWeatherViewModel: MutableLiveData<String> = MutableLiveData<String>()
    val stringLiveDataList_InFavouriteCityWeatherViewModel: LiveData<String> = stringMutableLiveData_InFavouriteCityWeatherViewModel

    private var intMutableLiveData_InFavouriteCityWeatherViewModel: MutableLiveData<Int> = MutableLiveData<Int>()
    val intLiveDataList_InFavouriteCityWeatherViewModel: LiveData<Int> = intMutableLiveData_InFavouriteCityWeatherViewModel

    private var weatherArrayListMutableLiveData_InFavouriteCityWeatherViewModel: MutableLiveData<ArrayList<Model_WeatherArrayList>> = MutableLiveData<ArrayList<Model_WeatherArrayList>>()
    val weatherArrayListLiveDataList_InFavouriteCityWeatherViewModel: LiveData<ArrayList<Model_WeatherArrayList>> = weatherArrayListMutableLiveData_InFavouriteCityWeatherViewModel

    private var cityMutableLiveData_InFavouriteCityWeatherViewModel: MutableLiveData<Model_City> = MutableLiveData<Model_City>()
    val cityLiveDataList_InFavouriteCityWeatherViewModel: LiveData<Model_City> = cityMutableLiveData_InFavouriteCityWeatherViewModel
*/
    private var forecastMutableLiveData_InFavouriteCityWeatherViewModel: MutableLiveData<Model_Forecast> = MutableLiveData<Model_Forecast>()
    val forecastLiveDataList_InFavouriteCityWeatherViewModel: LiveData<Model_Forecast> = forecastMutableLiveData_InFavouriteCityWeatherViewModel

/*

    fun getCod_FromRetrofit_InFavouriteCityWeatherViewModel(lat: String, lon: String, appid: String){

        Log.i("TAG", "getCod_FromRetrofit_InFavouriteCityWeatherViewModel: (before the viewModelScope):  lat: $lat , lon: $lon , appid: $appid")


        viewModelScope.launch(Dispatchers.IO){

            Log.i("TAG", "getCod_FromRetrofit_InFavouriteCityWeatherViewModel: (inside the viewModelScope): lat: $lat , lon: $lon , appid: $appid")
            Log.i("TAG", "getCod_FromRetrofit_InFavouriteCityWeatherViewModel: (inside the viewModelScope): "+  stringMutableLiveData_InFavouriteCityWeatherViewModel.postValue(
                weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteCityWeatherViewModel.getCod_FromRDS_InWeatherRepository(lat, lon, appid)))


            stringMutableLiveData_InFavouriteCityWeatherViewModel.postValue(
                weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteCityWeatherViewModel.getCod_FromRDS_InWeatherRepository(lat, lon, appid)
            )
        }

        Log.i("TAG", "getCod_FromRetrofit_InFavouriteCityWeatherViewModel: (after the viewModelScope):  lat: $lat , lon: $lon , appid: $appid")
    }

    fun getMessage_FromRetrofit_InFavouriteCityWeatherViewModel(lat: String, lon: String, appid: String) {

        Log.i("TAG", "getMessage_FromRetrofit_InFavouriteCityWeatherViewModel: (before the viewModelScope):  lat: $lat , lon: $lon , appid: $appid")


        viewModelScope.launch(Dispatchers.IO) {

            Log.i("TAG", "getMessage_FromRetrofit_InFavouriteCityWeatherViewModel: (inside the viewModelScope):  lat: $lat , lon: $lon , appid: $appid ")
            Log.i("TAG", "getMessage_FromRetrofit_InFavouriteCityWeatherViewModel: (inside the viewModelScope): "+ intMutableLiveData_InFavouriteCityWeatherViewModel.postValue(
                weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteCityWeatherViewModel.getMessage_FromRDS_InWeatherRepository(lat, lon, appid)))


            intMutableLiveData_InFavouriteCityWeatherViewModel.postValue(
                weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteCityWeatherViewModel.getMessage_FromRDS_InWeatherRepository(lat, lon, appid)
            )
        }

        Log.i("TAG", "getMessage_FromRetrofit_InFavouriteCityWeatherViewModel: (after the viewModelScope):  lat: $lat , lon: $lon , appid: $appid")
    }

    fun getCnt_FromRetrofit_InFavouriteCityWeatherViewModel(lat: String, lon: String, appid: String) {

        Log.i("TAG", "getCnt_FromRetrofit_InFavouriteCityWeatherViewModel: (before the viewModelScope):  lat: $lat , lon: $lon , appid: $appid")

        viewModelScope.launch(Dispatchers.IO) {

            Log.i("TAG", "getCnt_FromRetrofit_InFavouriteCityWeatherViewModel: (inside the viewModelScope): lat: $lat , lon: $lon , appid: $appid")
            Log.i("TAG", "getCnt_FromRetrofit_InFavouriteCityWeatherViewModel: (inside the viewModelScope): " + intMutableLiveData_InFavouriteCityWeatherViewModel.postValue(
                weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteCityWeatherViewModel.getCnt_FromRDS_InWeatherRepository(lat, lon, appid)))


            intMutableLiveData_InFavouriteCityWeatherViewModel.postValue(
                weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteCityWeatherViewModel.getCnt_FromRDS_InWeatherRepository(lat, lon, appid)
            )
        }

        Log.i("TAG", "getMessage_FromRetrofit_InFavouriteCityWeatherViewModel: (after the viewModelScope):  lat: $lat , lon: $lon , appid: $appid")

    }

    fun getList_FromRetrofit_InFavouriteCityWeatherViewModel(lat: String, lon: String, appid: String){

        Log.i("TAG", "getList_FromRetrofit_InFavouriteCityWeatherViewModel: (before the viewModelScope): lat: $lat , lon: $lon , appid: $appid")

        viewModelScope.launch(Dispatchers.IO) {

            Log.i("TAG", "getList_FromRetrofit_InFavouriteCityWeatherViewModel: (inside the viewModelScope):  lat: $lat , lon: $lon , appid: $appid")
            Log.i("TAG", "getList_FromRetrofit_InFavouriteCityWeatherViewModel: (inside the viewModelScope) : "+
                weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteCityWeatherViewModel.getList_FromRDS_InWeatherRepository(lat, lon, appid))


            weatherArrayListMutableLiveData_InFavouriteCityWeatherViewModel.postValue(
                weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteCityWeatherViewModel.getList_FromRDS_InWeatherRepository(lat, lon, appid)
            )
        }

        Log.i("TAG", "getList_FromRetrofit_InFavouriteCityWeatherViewModel: (after the viewModelScope): lat: $lat , lon: $lon , appid: $appid")
    }

    fun getCity_FromRetrofit_InFavouriteCityWeatherViewModel(city: String, appid:String){

        Log.i("TAG", "getCity_FromRetrofit_InFavouriteCityWeatherViewModel: (before the viewModelScope): city: $city , appid: $appid")


        viewModelScope.launch(Dispatchers.IO) {

            Log.i("TAG", "getCity_FromRetrofit_InFavouriteCityWeatherViewModel: (inside the viewModelScope): city: $city , appid: $appid")
            Log.i("TAG", "getCity_FromRetrofit_InFavouriteCityWeatherViewModel: (inside the viewModelScope): " + cityMutableLiveData_InFavouriteCityWeatherViewModel.postValue(
                weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteCityWeatherViewModel.getCity_FromRDS_InWeatherRepository(city, appid)
            ))

            cityMutableLiveData_InFavouriteCityWeatherViewModel.postValue(
                weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteCityWeatherViewModel.getCity_FromRDS_InWeatherRepository(city, appid)
            )
        }

        Log.i("TAG", "getCity_FromRetrofit_InFavouriteCityWeatherViewModel: (after the viewModelScope): city: $city , appid: $appid")

    }
*/
    fun getForecast_FromRetrofit_InFavouriteCityWeatherViewModel(lat: String, lon: String, appid: String){

        Log.i("TAG", "getForecast_FromRetrofit_InFavouriteCityWeatherViewModel: (before the viewModelScope): lat: $lat , lon: $lon , appid: $appid")

        viewModelScope.launch(Dispatchers.IO) {

            Log.i("TAG", "getForecast_FromRetrofit_InFavouriteCityWeatherViewModel: (inside the viewModelScope):  lat: $lat , lon: $lon , appid: $appid")
            Log.i("TAG", "getForecast_FromRetrofit_InFavouriteCityWeatherViewModel: (inside the viewModelScope) : "+
                    weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteCityWeatherViewModel.getForecast_FromRDS_InWeatherRepository(lat, lon, appid))


            forecastMutableLiveData_InFavouriteCityWeatherViewModel.postValue(
                weatherRepositoryInterface_Instance_ConstructorParameter_InFavouriteCityWeatherViewModel.getForecast_FromRDS_InWeatherRepository(lat, lon, appid)
            )
        }

        Log.i("TAG", "getForecast_FromRetrofit_InFavouriteCityWeatherViewModel: (after the viewModelScope): lat: $lat , lon: $lon , appid: $appid")
    }

}