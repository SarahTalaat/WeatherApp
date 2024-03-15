package com.example.productsmvvm.AllProducts.AllProductsViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.productsmvvm.Model.Products
import com.example.productsmvvm.Model.WeatherRepositoryInterface
import com.example.productsmvvm.Model.WeatherResponse
import com.example.weatherapplication.Model.Model_Forecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrentWeatherViewModel(private val currentWeatherRepositoryInterfaceInstance_ConstructorParameter_InCurrentWeatherViewModel: WeatherRepositoryInterface):ViewModel() {

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


    private var forecastMutableLiveDataList_InCurrentWeatherViewModel: MutableLiveData<> = MutableLiveData<Model_Forecast>()
    val forecastLiveDataList_InAllProductsViewModel: LiveData<Model_Forecast> = forecastMutableLiveDataList_InCurrentWeatherViewModel

    fun getCod_FromRetrofit_InCurrentWeatherViewModel(lat: String, lon: String, appid: String){
        viewModelScope.launch(Dispatchers.IO){
            forecastMutableLiveDataList_InCurrentWeatherViewModel.postValue(currentWeatherRepositoryInterfaceInstance_ConstructorParameter_InCurrentWeatherViewModel.getCod_FromRDS_InProductsRepository(lat, lon, appid))
        }
    }


}