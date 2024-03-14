package com.example.productsmvvm.FavouriteProducts.FavouriteProductsViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.productsmvvm.Model.ProductsRepositoryInterface


//1.Send repository
//2.Implement the factory
class FavouriteProductsViewModelFactory_LDS(private val  productsRepositoryInterface_Instance_ConstructorParameter_InFavouriteProductsViewModelFactory: ProductsRepositoryInterface ): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavouriteProductsViewModel::class.java)){
            FavouriteProductsViewModel(productsRepositoryInterface_Instance_ConstructorParameter_InFavouriteProductsViewModelFactory) as T
        }else{
            throw IllegalArgumentException("Favourite ViewModel class not found")
        }
    }

}