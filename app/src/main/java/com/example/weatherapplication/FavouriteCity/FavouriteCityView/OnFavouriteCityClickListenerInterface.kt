package com.example.productsmvvm.FavouriteProducts.FavouriteProductsView

import com.example.weatherapplication.Model.Model_FavouriteCity


interface OnFavouriteCityClickListenerInterface {
    //Implemented in favourite products activity
    fun onClick_DeleteFavouriteCityFromFavouriteCityFragment_InOnFavouriteClickListenerInterface(city:Model_FavouriteCity)

    fun onClick_NavigateToFavouriteCityWeatherActivity_InOnFavouriteClickListenerInterface(latitude: String?, longitude: String?)

}
