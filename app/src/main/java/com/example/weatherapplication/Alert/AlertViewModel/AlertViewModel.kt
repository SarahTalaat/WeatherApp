package com.example.weatherapplication.Alert.AlertViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.productsmvvm.Model.Products
import com.example.weatherapplication.Repository.WeatherRepositoryInterface
import com.example.weatherapplication.Model.AlertModel.APIModel.Model_Alert
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlertViewModel(private val weatherRepositoryInterface_Instance_ConstructorParameter_InAlertViewModel: WeatherRepositoryInterface):ViewModel() {

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

    private var alertMutableLiveData_InAlertViewModel: MutableLiveData<Model_Alert> = MutableLiveData<Model_Alert>()
    val alertLiveDataList_InAlertViewModel: LiveData<Model_Alert> = alertMutableLiveData_InAlertViewModel

    private var alertMutableLiveData_ModelTime_InAlertViewModel: MutableLiveData<Model_Time> = MutableLiveData<Model_Time>()
    val alertLiveDataList_ModelTime_InAlertViewModel: LiveData<Model_Time> = alertMutableLiveData_ModelTime_InAlertViewModel



    fun getAlert_FromRetrofit_InAlertViewModel(lat: String, lon: String, appid: String){

        Log.i("TAG", "getAlert_FromRetrofit_InAlertViewModel: (before the viewModelScope): lat: $lat , lon: $lon , appid: $appid")

        viewModelScope.launch(Dispatchers.IO) {

            Log.i("TAG", "getAlert_FromRetrofit_InAlertViewModel: (inside the viewModelScope):  lat: $lat , lon: $lon , appid: $appid")
            Log.i("TAG", "getAlert_FromRetrofit_InAlertViewModel: (inside the viewModelScope) : "+
                    weatherRepositoryInterface_Instance_ConstructorParameter_InAlertViewModel.getAlert_FromRDS_InWeatherRepository(lat, lon, appid))


            alertMutableLiveData_InAlertViewModel.postValue(
                weatherRepositoryInterface_Instance_ConstructorParameter_InAlertViewModel.getAlert_FromRDS_InWeatherRepository(lat, lon, appid)
            )
        }

        Log.i("TAG", "getAlert_FromRetrofit_InAlertViewModel: (after the viewModelScope): lat: $lat , lon: $lon , appid: $appid")
    }
/*
    fun getAlert_FromDatabase_InAlertViewModel(time: Model_Time){
        viewModelScope.launch(Dispatchers.IO) {

            Log.i("TAG", "getAlert_FromDatabase_InAlertViewModel: (inside the viewModelScope): ")
            Log.i("TAG", "getAlert_FromDatabase_InAlertViewModel: (inside the viewModelScope) : "+
                    weatherRepositoryInterface_Instance_ConstructorParameter_InAlertViewModel.getAllStoredModelTime_FromLDS_InWeatherRepository())


            alertMutableLiveData_ModelTime_InAlertViewModel.postValue(
                weatherRepositoryInterface_Instance_ConstructorParameter_InAlertViewModel.getAllStoredModelTime_FromLDS_InWeatherRepository()
            )

        }
    }
    */

}