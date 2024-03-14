package com.example.productsmvvm.Network

import com.example.productsmvvm.Model.Products
import kotlinx.coroutines.flow.Flow

private const val TAB = "AllProductsFeature"
class ProductsRemoteDataSourceImplementation private constructor() : ProductsRemoteDataSourceInterface{

    private val productsService: ProductsServiceInterface by lazy {
        RetrofitHelper.getRetrofit_Instance_InRetrofitHelper().create(ProductsServiceInterface::class.java)
    }
    override suspend fun getAllProductsOverNetwork_InRDS(): List<Products> {

        val response = productsService.getAllProducts_FromApiEndPoint_InProductsService().products
        return response


    }

    companion object{
        private var instance: ProductsRemoteDataSourceImplementation?=null
        fun getProductsRemoteDataSourceImplementationInstance(): ProductsRemoteDataSourceImplementation{
            return instance?: synchronized(this){
                val temp = ProductsRemoteDataSourceImplementation()
                instance = temp
                temp
            }
        }
    }
}