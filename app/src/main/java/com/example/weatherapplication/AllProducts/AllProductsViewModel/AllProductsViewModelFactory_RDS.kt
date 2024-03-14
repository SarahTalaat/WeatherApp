package com.example.productsmvvm.AllProducts.AllProductsViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.productsmvvm.Model.ProductsRepositoryInterface

class AllProductsViewModelFactory_RDS(private val  productsRepositoryInterface_Instance_ConstructorParameter_InAllProductsViewModelFactory: ProductsRepositoryInterface): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AllProductsViewModel::class.java)){
            AllProductsViewModel(productsRepositoryInterface_Instance_ConstructorParameter_InAllProductsViewModelFactory) as T
        }else{
            throw IllegalArgumentException("AllProducts ViewModel class not found")
        }
    }
}