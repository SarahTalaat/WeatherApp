package com.example.productsmvvm.FavouriteProducts.FavouriteProductsViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productsmvvm.Model.Products
import com.example.productsmvvm.Model.ProductsRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteProductsViewModel(private val productsRepositoryInterface_Instance_ConstructorParameter_InFavouriteProductsViewModel: ProductsRepositoryInterface): ViewModel() {
                //_product
    private var productsMutableLiveDataList_InFavouriteProductsViewModel: MutableLiveData<List<Products>> = MutableLiveData<List<Products>>()
        //products
    val productsLiveDataList_InFavouriteProductsViewModel: LiveData<List<Products>> = productsMutableLiveDataList_InFavouriteProductsViewModel


    init {
        getAllLocalProducts_StoredInDatabase_InFavouriteProductsViewModel()
    }

    fun deleteProduct_InFavouriteProductsViewModel(product:Products){
        viewModelScope.launch(Dispatchers.IO){
            productsRepositoryInterface_Instance_ConstructorParameter_InFavouriteProductsViewModel.deleteProduct_FromLDS_InProductsRepository(product)
            getAllLocalProducts_StoredInDatabase_InFavouriteProductsViewModel()
        }
    }

    fun getAllLocalProducts_StoredInDatabase_InFavouriteProductsViewModel(){
        viewModelScope.launch(Dispatchers.IO){
            productsRepositoryInterface_Instance_ConstructorParameter_InFavouriteProductsViewModel.getAllStoredProducts_FromLDS_InProductsRepository().collect{ products -> productsMutableLiveDataList_InFavouriteProductsViewModel.postValue(products)}
        }
    }

}