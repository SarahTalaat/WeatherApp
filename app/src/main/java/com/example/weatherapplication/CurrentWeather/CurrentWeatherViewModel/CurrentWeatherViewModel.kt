package com.example.productsmvvm.AllProducts.AllProductsViewModel

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

class CurrentWeatherViewModel(private val currentWeatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel: WeatherRepositoryInterface):ViewModel() {

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
    private var stringMutableLiveData_InCurrentWeatherViewModel: MutableLiveData<String> = MutableLiveData<String>()
    val stringLiveDataList_InCurrentWeatherViewModel: LiveData<String> = stringMutableLiveData_InCurrentWeatherViewModel

    private var intMutableLiveData_InCurrentWeatherViewModel: MutableLiveData<Int> = MutableLiveData<Int>()
    val intLiveDataList_InCurrentWeatherViewModel: LiveData<Int> = intMutableLiveData_InCurrentWeatherViewModel

    private var weatherArrayListMutableLiveData_InCurrentWeatherViewModel: MutableLiveData<ArrayList<Model_WeatherArrayList>> = MutableLiveData<ArrayList<Model_WeatherArrayList>>()
    val weatherArrayListLiveDataList_InCurrentWeatherViewModel: LiveData<ArrayList<Model_WeatherArrayList>> = weatherArrayListMutableLiveData_InCurrentWeatherViewModel

    private var cityMutableLiveData_InCurrentWeatherViewModel: MutableLiveData<Model_City> = MutableLiveData<Model_City>()
    val cityLiveDataList_InCurrentWeatherViewModel: LiveData<Model_City> = cityMutableLiveData_InCurrentWeatherViewModel

   */
    private var forecastMutableLiveData_InCurrentWeatherViewModel: MutableLiveData<Model_Forecast> = MutableLiveData<Model_Forecast>()
    val forecastLiveDataList_InCurrentWeatherViewModel: LiveData<Model_Forecast> = forecastMutableLiveData_InCurrentWeatherViewModel


/*
    fun getCod_FromRetrofit_InCurrentWeatherViewModel(lat: String, lon: String, appid: String){

        Log.i("TAG", "getCod_FromRetrofit_InCurrentWeatherViewModel: (before the viewModelScope):  lat: $lat , lon: $lon , appid: $appid")


        viewModelScope.launch(Dispatchers.IO){

            Log.i("TAG", "getCod_FromRetrofit_InCurrentWeatherViewModel: (inside the viewModelScope): lat: $lat , lon: $lon , appid: $appid")
            Log.i("TAG", "getCod_FromRetrofit_InCurrentWeatherViewModel: (inside the viewModelScope): "+  stringMutableLiveData_InCurrentWeatherViewModel.postValue(
                currentWeatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel.getCod_FromRDS_InWeatherRepository(lat, lon, appid)))


            stringMutableLiveData_InCurrentWeatherViewModel.postValue(
                currentWeatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel.getCod_FromRDS_InWeatherRepository(lat, lon, appid)
            )
        }

        Log.i("TAG", "getCod_FromRetrofit_InCurrentWeatherViewModel: (after the viewModelScope):  lat: $lat , lon: $lon , appid: $appid")
    }

    fun getMessage_FromRetrofit_InCurrentWeatherViewModel(lat: String, lon: String, appid: String) {

        Log.i("TAG", "getMessage_FromRetrofit_InCurrentWeatherViewModel: (before the viewModelScope):  lat: $lat , lon: $lon , appid: $appid")


        viewModelScope.launch(Dispatchers.IO) {

            Log.i("TAG", "getMessage_FromRetrofit_InCurrentWeatherViewModel: (inside the viewModelScope):  lat: $lat , lon: $lon , appid: $appid ")
            Log.i("TAG", "getMessage_FromRetrofit_InCurrentWeatherViewModel: (inside the viewModelScope): "+ intMutableLiveData_InCurrentWeatherViewModel.postValue(
                currentWeatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel.getMessage_FromRDS_InWeatherRepository(lat, lon, appid)))


            intMutableLiveData_InCurrentWeatherViewModel.postValue(
                currentWeatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel.getMessage_FromRDS_InWeatherRepository(lat, lon, appid)
            )
        }

        Log.i("TAG", "getMessage_FromRetrofit_InCurrentWeatherViewModel: (after the viewModelScope):  lat: $lat , lon: $lon , appid: $appid")
    }

    fun getCnt_FromRetrofit_InCurrentWeatherViewModel(lat: String, lon: String, appid: String) {

        Log.i("TAG", "getCnt_FromRetrofit_InCurrentWeatherViewModel: (before the viewModelScope):  lat: $lat , lon: $lon , appid: $appid")

        viewModelScope.launch(Dispatchers.IO) {

            Log.i("TAG", "getCnt_FromRetrofit_InCurrentWeatherViewModel: (inside the viewModelScope): lat: $lat , lon: $lon , appid: $appid")
            Log.i("TAG", "getCnt_FromRetrofit_InCurrentWeatherViewModel: (inside the viewModelScope): " + intMutableLiveData_InCurrentWeatherViewModel.postValue(
                currentWeatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel.getCnt_FromRDS_InWeatherRepository(lat, lon, appid)))


            intMutableLiveData_InCurrentWeatherViewModel.postValue(
                currentWeatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel.getCnt_FromRDS_InWeatherRepository(lat, lon, appid)
            )
        }

        Log.i("TAG", "getMessage_FromRetrofit_InCurrentWeatherViewModel: (after the viewModelScope):  lat: $lat , lon: $lon , appid: $appid")

    }

    fun getList_FromRetrofit_InCurrentWeatherViewModel(lat: String, lon: String, appid: String){

        Log.i("TAG", "getList_FromRetrofit_InCurrentWeatherViewModel: (before the viewModelScope): lat: $lat , lon: $lon , appid: $appid")

        viewModelScope.launch(Dispatchers.IO) {

            Log.i("TAG", "getList_FromRetrofit_InCurrentWeatherViewModel: (inside the viewModelScope):  lat: $lat , lon: $lon , appid: $appid")
            Log.i("TAG", "getList_FromRetrofit_InCurrentWeatherViewModel: (inside the viewModelScope) : "+
                currentWeatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel.getList_FromRDS_InWeatherRepository(lat, lon, appid))


            weatherArrayListMutableLiveData_InCurrentWeatherViewModel.postValue(
                currentWeatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel.getList_FromRDS_InWeatherRepository(lat, lon, appid)
            )
        }

        Log.i("TAG", "getList_FromRetrofit_InCurrentWeatherViewModel: (after the viewModelScope): lat: $lat , lon: $lon , appid: $appid")
    }

    fun getCity_FromRetrofit_InCurrentWeatherViewModel(city: String, appid:String){

        Log.i("TAG", "getCity_FromRetrofit_InCurrentWeatherViewModel: (before the viewModelScope): city: $city , appid: $appid")


        viewModelScope.launch(Dispatchers.IO) {

            Log.i("TAG", "getCity_FromRetrofit_InCurrentWeatherViewModel: (inside the viewModelScope): city: $city , appid: $appid")
            Log.i("TAG", "getCity_FromRetrofit_InCurrentWeatherViewModel: (inside the viewModelScope): " + cityMutableLiveData_InCurrentWeatherViewModel.postValue(
                currentWeatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel.getCity_FromRDS_InWeatherRepository(city, appid)
            ))

            cityMutableLiveData_InCurrentWeatherViewModel.postValue(
                currentWeatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel.getCity_FromRDS_InWeatherRepository(city, appid)
            )
        }

        Log.i("TAG", "getCity_FromRetrofit_InCurrentWeatherViewModel: (after the viewModelScope): city: $city , appid: $appid")

    }
*/
    fun getForecast_FromRetrofit_InCurrentWeatherViewModel(lat: String, lon: String, appid: String){

        Log.i("TAG", "getForecast_FromRetrofit_InCurrentWeatherViewModel: (before the viewModelScope): lat: $lat , lon: $lon , appid: $appid")

        viewModelScope.launch(Dispatchers.IO) {

            Log.i("TAG", "getForecast_FromRetrofit_InCurrentWeatherViewModel: (inside the viewModelScope):  lat: $lat , lon: $lon , appid: $appid")
            Log.i("TAG", "getForecast_FromRetrofit_InCurrentWeatherViewModel: (inside the viewModelScope) : "+
                    currentWeatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel.getForecast_FromRDS_InWeatherRepository(lat, lon, appid))


            forecastMutableLiveData_InCurrentWeatherViewModel.postValue(
                currentWeatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel.getForecast_FromRDS_InWeatherRepository(lat, lon, appid)
            )
        }

        Log.i("TAG", "getForecast_FromRetrofit_InCurrentWeatherViewModel: (after the viewModelScope): lat: $lat , lon: $lon , appid: $appid")
    }

}