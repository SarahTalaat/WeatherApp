package com.example.productsmvvm.FavouriteProducts.FavouriteProductsView


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.Model.Model_FavouriteCity
import com.example.weatherapplication.R
import java.util.ArrayList

class FavouriteProductsAdapter: RecyclerView.Adapter<FavouriteProductsAdapter.MyViewHolder_InFavouriteProductsAdapter> {

    private final var tab: String = "team"

    var context_Instance_InFavouriteCityAdapter: Context
    var favouriteCityArrayList_InFavouriteCityAdapter: ArrayList<Model_FavouriteCity>
    var onFavouriteCityClickListenerInterface_Instance_InFavouriteProductsAdapterCity: OnFavouriteCityClickListenerInterface

    public constructor(context_Instance_ConstructorParameter_InFavouriteProductsAdapter: Context, productsArrayList_ConstructorParameter_InFavouriteProductsAdapter: ArrayList<Model_FavouriteCity>, onFavouriteClickListenerInterfaceInstance_ConstructorParameter_InFavouriteProductsAdapterCity: OnFavouriteCityClickListenerInterface){
        this.context_Instance_InFavouriteCityAdapter=context_Instance_ConstructorParameter_InFavouriteProductsAdapter
        this.favouriteCityArrayList_InFavouriteCityAdapter=productsArrayList_ConstructorParameter_InFavouriteProductsAdapter
        this.onFavouriteCityClickListenerInterface_Instance_InFavouriteProductsAdapterCity = onFavouriteClickListenerInterfaceInstance_ConstructorParameter_InFavouriteProductsAdapterCity
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder_InFavouriteProductsAdapter {
        var inflater: LayoutInflater = context_Instance_InFavouriteCityAdapter.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as (LayoutInflater)
        var view: View = inflater.inflate(R.layout.card_favourite,parent,false)
        var myViewHolder_InFavouriteProductsAdapter: MyViewHolder_InFavouriteProductsAdapter = MyViewHolder_InFavouriteProductsAdapter(view)
        return  myViewHolder_InFavouriteProductsAdapter
    }

    override fun getItemCount(): Int {
        return favouriteCityArrayList_InFavouriteCityAdapter.size
    }

    fun setFavouriteCityList_InFavouriteCityAdapter(favouriteCityArrayList: ArrayList<Model_FavouriteCity>){
        Log.i("TAG", "setProductList_InFavouriteProductsAdapter: productArrayList :" + favouriteCityArrayList)
        this.favouriteCityArrayList_InFavouriteCityAdapter = favouriteCityArrayList
        notifyDataSetChanged()
    }

    class MyViewHolder_InFavouriteProductsAdapter: RecyclerView.ViewHolder {

            var tv_cityName_InFavouriteCityAdapter: TextView
            var btn_deleteFromFavourite_InFavouriteCityAdapter: Button


        constructor(@NonNull itemView: View): super(itemView){
                tv_cityName_InFavouriteCityAdapter = itemView.findViewById(R.id.tv_cityName)
                btn_deleteFromFavourite_InFavouriteCityAdapter = itemView.findViewById(R.id.btn_delete)
            }
    }

    override fun onBindViewHolder(holder: MyViewHolder_InFavouriteProductsAdapter, position: Int) {

        holder.tv_cityName_InFavouriteCityAdapter.setText(favouriteCityArrayList_InFavouriteCityAdapter.get(position).cityName)
        holder.btn_deleteFromFavourite_InFavouriteCityAdapter.setOnClickListener(){
            onFavouriteCityClickListenerInterface_Instance_InFavouriteProductsAdapterCity.onClick_DeleteFavouriteCityFromFavouriteCityFragment_InOnFavouriteClickListenerInterface(favouriteCityArrayList_InFavouriteCityAdapter.get(position))
        }
        holder.itemView.setOnClickListener(){
            val city = favouriteCityArrayList_InFavouriteCityAdapter.get(position)
            onFavouriteCityClickListenerInterface_Instance_InFavouriteProductsAdapterCity.onClick_NavigateToFavouriteCityWeatherActivity_InOnFavouriteClickListenerInterface(city.latitude , city.longitude)
        }

    }

}

