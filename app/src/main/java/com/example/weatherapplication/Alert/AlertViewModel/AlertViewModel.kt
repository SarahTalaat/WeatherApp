package com.example.weatherapplication.Alert.AlertViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.productsmvvm.Model.Products
import com.example.weatherapplication.Repository.WeatherRepositoryInterface
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity
import com.example.weatherapplication.Network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AlertViewModel(private val weatherRepositoryInterface_Instance_ConstructorParameter_InAlertViewModel: WeatherRepositoryInterface):ViewModel() {

    private var alertMutableStateFlow_InAlertViewModel: MutableStateFlow<ApiState> = MutableStateFlow<ApiState>(ApiState.Loading)
    val alertStateFlow_InAlertViewModel: MutableStateFlow<ApiState> = alertMutableStateFlow_InAlertViewModel

    private var alertMutableLiveData_ModelTime_InAlertViewModel: MutableLiveData<List<Model_Time>> = MutableLiveData<List<Model_Time>>()
    val alertLiveDataList_ModelTime_InAlertViewModel:LiveData<List<Model_Time>> = alertMutableLiveData_ModelTime_InAlertViewModel


    init {
        getAllLocalModelTime_StoredInDatabase_InAlertViewModel()
    }


    fun getAlert_FromRetrofit_InAlertViewModel(lat: String, lon: String, appid: String){

        Log.i("TAG", "getAlert_FromRetrofit_InAlertViewModel: (before the viewModelScope): lat: $lat , lon: $lon , appid: $appid")
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepositoryInterface_Instance_ConstructorParameter_InAlertViewModel.getAlert_FromRDS_InWeatherRepository(lat, lon, appid)
                .catch { e ->
                    alertMutableStateFlow_InAlertViewModel.value = ApiState.Failure(e)
                }
                .collect{data ->
                    alertMutableStateFlow_InAlertViewModel.value=ApiState.Success_ModelAlert_InApiState(data)
                }

        }

        Log.i("TAG", "getAlert_FromRetrofit_InAlertViewModel: (after the viewModelScope): lat: $lat , lon: $lon , appid: $appid")
    }

    fun deleteModelTime_InAlertViewModel(modelTime: Model_Time){
        viewModelScope.launch(Dispatchers.IO){
            weatherRepositoryInterface_Instance_ConstructorParameter_InAlertViewModel.deleteModelTime_FromLDS_InWeatherRepository(modelTime)
            getAllLocalModelTime_StoredInDatabase_InAlertViewModel()
        }
    }

    fun insertModelTime_InAlertViewModel(modelTime: Model_Time){
        viewModelScope.launch(Dispatchers.IO){
            weatherRepositoryInterface_Instance_ConstructorParameter_InAlertViewModel.insertModelTime_FromLDS_InWeatherRepository(modelTime)
            getAllLocalModelTime_StoredInDatabase_InAlertViewModel()
        }
    }

    fun getAllLocalModelTime_StoredInDatabase_InAlertViewModel(){
        viewModelScope.launch(Dispatchers.IO){
            weatherRepositoryInterface_Instance_ConstructorParameter_InAlertViewModel.
            getAllStoredModelTime_FromLDS_InWeatherRepository()
                .collect{ modelTime ->
                    alertMutableLiveData_ModelTime_InAlertViewModel.postValue(modelTime)}
        }
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
    fun getAlert_FromDatabase_InAlertViewModel(){
        viewModelScope.launch(Dispatchers.IO) {
            Log.i("TAG", "getAlert_FromDatabase_InAlertViewModel: (inside the viewModelScope): ")
            Log.i("TAG", "getAlert_FromDatabase_InAlertViewModel: (inside the viewModelScope) : "+
                    weatherRepositoryInterface_Instance_ConstructorParameter_InAlertViewModel.getAllStoredModelTime_FromLDS_InWeatherRepository())

            // Collect the flow
            weatherRepositoryInterface_Instance_ConstructorParameter_InAlertViewModel
                .getAllStoredModelTime_FromLDS_InWeatherRepository()
                .collect { modelTimeList ->
                    // Post the value to LiveData
                    alertMutableLiveData_ModelTime_InAlertViewModel.postValue(modelTimeList)
                }
        }
    }
*/
}