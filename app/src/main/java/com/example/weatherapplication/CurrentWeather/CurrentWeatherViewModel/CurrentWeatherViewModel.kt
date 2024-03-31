package com.example.productsmvvm.AllProducts.AllProductsViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.productsmvvm.Model.Products
import com.example.weatherapplication.Repository.WeatherRepositoryInterface
import com.example.weatherapplication.Model.CurrentWeatherModel.APIModel.Model_Forecast
import com.example.weatherapplication.Network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CurrentWeatherViewModel(private val weatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel: WeatherRepositoryInterface):ViewModel() {


    private var forecastMutableStateFlow_InCurrentWeatherViewModel: MutableStateFlow<ApiState> = MutableStateFlow<ApiState>(ApiState.Loading)
    val forecastStateFlow_InCurrentWeatherViewModel: StateFlow<ApiState> = forecastMutableStateFlow_InCurrentWeatherViewModel

    fun getForecast_FromRetrofit_InCurrentWeatherViewModel(lat: String, lon: String, appid: String){

        Log.i("TAG", "getForecast_FromRetrofit_InCurrentWeatherViewModel: (before the viewModelScope): lat: $lat , lon: $lon , appid: $appid")
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel.getForecast_FromRDS_InWeatherRepository(lat, lon, appid)
                .catch { e ->
                    forecastMutableStateFlow_InCurrentWeatherViewModel.value = ApiState.Failure(e)
                }
                .collect{data ->
                    forecastMutableStateFlow_InCurrentWeatherViewModel.value= ApiState.Success_ModelForecast_InApiState(data)
                }

        }


        Log.i("TAG", "getForecast_FromRetrofit_InCurrentWeatherViewModel: (after the viewModelScope): lat: $lat , lon: $lon , appid: $appid")
    }

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


}