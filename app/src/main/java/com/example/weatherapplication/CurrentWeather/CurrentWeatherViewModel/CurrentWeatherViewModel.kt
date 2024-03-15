package com.example.productsmvvm.AllProducts.AllProductsViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.productsmvvm.Model.Products
import com.example.productsmvvm.Model.WeatherRepositoryInterface
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


    private var stringMutableLiveData_InCurrentWeatherViewModel: MutableLiveData<String> = MutableLiveData<String>()
    val stringLiveDataList_InCurrentWeatherViewModel: LiveData<String> = stringMutableLiveData_InCurrentWeatherViewModel

    private var intMutableLiveData_InCurrentWeatherViewModel: MutableLiveData<Int> = MutableLiveData<Int>()
    val intLiveDataList_InCurrentWeatherViewModel: LiveData<Int> = intMutableLiveData_InCurrentWeatherViewModel

    private var weatherArrayListMutableLiveData_InCurrentWeatherViewModel: MutableLiveData<ArrayList<Model_WeatherArrayList>> = MutableLiveData<ArrayList<Model_WeatherArrayList>>()
    val weatherArrayListLiveDataList_InCurrentWeatherViewModel: LiveData<ArrayList<Model_WeatherArrayList>> = weatherArrayListMutableLiveData_InCurrentWeatherViewModel

    private var cityMutableLiveData_InCurrentWeatherViewModel: MutableLiveData<Model_City> = MutableLiveData<Model_City>()
    val cityLiveDataList_InCurrentWeatherViewModel: LiveData<Model_City> = cityMutableLiveData_InCurrentWeatherViewModel


    fun getCod_FromRetrofit_InCurrentWeatherViewModel(lat: String, lon: String, appid: String){


        viewModelScope.launch(Dispatchers.IO){

            Log.i("TAG", "getCod_FromRetrofit_InCurrentWeatherViewModel: (inside the viewModelScope): lat: $lat , lon: $lon , appid: $appid")
            Log.i("TAG", "getCod_FromRetrofit_InCurrentWeatherViewModel: (inside the viewModelScope): "+  stringMutableLiveData_InCurrentWeatherViewModel.postValue(
                currentWeatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel.getCod_FromRDS_InProductsRepository(lat, lon, appid)))


            stringMutableLiveData_InCurrentWeatherViewModel.postValue(
                currentWeatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel.getCod_FromRDS_InProductsRepository(lat, lon, appid)
            )
        }
    }

    fun getMessage_FromRetrofit_InCurrentWeatherViewModel(lat: String, lon: String, appid: String) {
        viewModelScope.launch(Dispatchers.IO) {

            Log.i("TAG", "getMessage_FromRetrofit_InCurrentWeatherViewModel: (inside the viewModelScope):  lat: $lat , lon: $lon , appid: $appid ")
            Log.i("TAG", "getMessage_FromRetrofit_InCurrentWeatherViewModel: (inside the viewModelScope): "+ intMutableLiveData_InCurrentWeatherViewModel.postValue(
                currentWeatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel.getMessage_FromRDS_InProductsRepository(lat, lon, appid)))


            intMutableLiveData_InCurrentWeatherViewModel.postValue(
                currentWeatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel.getMessage_FromRDS_InProductsRepository(lat, lon, appid)
            )
        }
    }

    fun getCnt_FromRetrofit_InCurrentWeatherViewModel(lat: String, lon: String, appid: String) {
        viewModelScope.launch(Dispatchers.IO) {

            Log.i("TAG", "getCnt_FromRetrofit_InCurrentWeatherViewModel: (inside the viewModelScope): lat: $lat , lon: $lon , appid: $appid")
            Log.i("TAG", "getCnt_FromRetrofit_InCurrentWeatherViewModel: (inside the viewModelScope): " + intMutableLiveData_InCurrentWeatherViewModel.postValue(
                currentWeatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel.getCnt_FromRDS_InProductsRepository(lat, lon, appid)))


            intMutableLiveData_InCurrentWeatherViewModel.postValue(
                currentWeatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel.getCnt_FromRDS_InProductsRepository(lat, lon, appid)
            )
        }
    }

    fun getList_FromRetrofit_InCurrentWeatherViewModel(lat: String, lon: String, appid: String){


        viewModelScope.launch(Dispatchers.IO) {

            Log.i("TAG", "getList_FromRetrofit_InCurrentWeatherViewModel: (inside the viewModelScope):  lat: $lat , lon: $lon , appid: $appid")
            Log.i("TAG", "getList_FromRetrofit_InCurrentWeatherViewModel: (inside the viewModelScope) : "+ weatherArrayListMutableLiveData_InCurrentWeatherViewModel.postValue(
                currentWeatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel.getList_FromRDS_InProductsRepository(lat, lon, appid)))


            weatherArrayListMutableLiveData_InCurrentWeatherViewModel.postValue(
                currentWeatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel.getList_FromRDS_InProductsRepository(lat, lon, appid)
            )
        }
    }

    fun getCity_FromRetrofit_InCurrentWeatherViewModel(city: String, appid:String){



        viewModelScope.launch(Dispatchers.IO) {

            Log.i("TAG", "getCity_FromRetrofit_InCurrentWeatherViewModel: (inside the viewModelScope): city: $city , appid: $appid")
            Log.i("TAG", "getCity_FromRetrofit_InCurrentWeatherViewModel: (inside the viewModelScope): " + cityMutableLiveData_InCurrentWeatherViewModel.postValue(
                currentWeatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel.getCity_FromRDS_InProductsRepository(city, appid)
            ))

            cityMutableLiveData_InCurrentWeatherViewModel.postValue(
                currentWeatherRepositoryInterface_Instance_ConstructorParameter_InCurrentWeatherViewModel.getCity_FromRDS_InProductsRepository(city, appid)
            )
        }
    }

}