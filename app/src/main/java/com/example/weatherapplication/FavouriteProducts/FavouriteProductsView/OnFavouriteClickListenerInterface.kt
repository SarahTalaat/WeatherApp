package com.example.productsmvvm.FavouriteProducts.FavouriteProductsView

import com.example.productsmvvm.Model.Products

interface OnFavouriteClickListenerInterface {
    //Implemented in favourite products activity
    fun onClick_DeleteProductFromFavouriteProductsActivity_InOnFavouriteClickListenerInterface(product: Products)
}