package com.example.productsmvvm.FavouriteProducts.FavouriteProductsView

import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity


interface OnAlertClickListenerInterface {
    //Implemented in favourite products activity
    fun onClick_DeleteModelTimeFromAlertFragment_InOnAlertClickListenerInterface(modelTime: Model_Time)

    fun onClick_InserModelTimeToAlertFragment_InOnAlertClickListenerInterface(modelTime: Model_Time)

}

