package com.example.productsmvvm.CurrentWeather.CurrentWeatherView

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapplication.Model_City
import com.example.weatherapplication.Model_WeatherArrayList
import com.example.weatherapplication.R
//import com.example.productsmvvm.Model.Products
//import com.example.productsmvvm.R
import java.util.ArrayList
/*
class CurrentWeatherAdapter: RecyclerView.Adapter<CurrentWeatherAdapter.MyViewHolder_InCurrentWeatherAdapter>  {


    private final var tab: String = "team"

    var context_Instance_InCurrentWeatherAdapter: Context
    var productsArrayList_InCurrentWeatherAdapter: ArrayList<Products>
    var onCurrentWeatherClickListenerInterface_Instance_InWeatherAdapter: OnCurrentWeatherClickListenerInterface

    public constructor(context_Instance_ConstructorParameter_InCurrentWeatherAdapter: Context, productsArrayList_ConstructorParameter_InCurrentWeatherAdapter: ArrayList<Products>, onCurrentWeatherClickListenerInterfaceInstance_ConstructorParameter_InWeatherAdapter: OnCurrentWeatherClickListenerInterface){
        this.context_Instance_InCurrentWeatherAdapter=context_Instance_ConstructorParameter_InCurrentWeatherAdapter
        this.productsArrayList_InCurrentWeatherAdapter=productsArrayList_ConstructorParameter_InCurrentWeatherAdapter
        this.onCurrentWeatherClickListenerInterface_Instance_InWeatherAdapter = onCurrentWeatherClickListenerInterfaceInstance_ConstructorParameter_InWeatherAdapter
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder_InCurrentWeatherAdapter {
        var inflater: LayoutInflater = context_Instance_InCurrentWeatherAdapter.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as (LayoutInflater)
        var view: View = inflater.inflate(R.layout.activity_all_products_details,parent,false)
        var myViewHolder_InCurrentWeatherAdapter: MyViewHolder_InCurrentWeatherAdapter = MyViewHolder_InCurrentWeatherAdapter(view)
        return  myViewHolder_InCurrentWeatherAdapter
    }

    override fun getItemCount(): Int {
        return productsArrayList_InCurrentWeatherAdapter.size
    }

    fun setProductList_InCurrentWeatherAdapter(productsArrayList: ArrayList<Products>){
        Log.i("TAG", "setProductList_InCurrentWeatherAdapter: productArrayList :" + productsArrayList)
        this.productsArrayList_InCurrentWeatherAdapter = productsArrayList
        notifyDataSetChanged()
    }

    class MyViewHolder_InCurrentWeatherAdapter: RecyclerView.ViewHolder {

        var tv_title_value_CurrentWeather: TextView
        var tv_descroption_value_CurrentWeather: TextView
        var tv_id_value_CurrentWeather: TextView
        var tv_price_value_CurrentWeather: TextView
        var img_product_CurrentWeather: ImageView
        var btn_addToFavourite_CurrentWeather: Button


        constructor(@NonNull itemView: View): super(itemView){
            tv_title_value_CurrentWeather = itemView.findViewById(R.id.tv_title_value_CurrentWeather)
            tv_descroption_value_CurrentWeather = itemView.findViewById(R.id.tv_description_value_CurrentWeather)
            tv_id_value_CurrentWeather = itemView.findViewById(R.id.tv_id_value_CurrentWeather)
            tv_price_value_CurrentWeather = itemView.findViewById(R.id.tv_id_value_CurrentWeather)
            img_product_CurrentWeather = itemView.findViewById(R.id.img_product_CurrentWeather)
            btn_addToFavourite_CurrentWeather = itemView.findViewById(R.id.btn_addToFavourite_CurrentWeather)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder_InCurrentWeatherAdapter, position: Int) {
        var imageUrl_InMyViewHolder_InCurrentWeatherAdapter: String = productsArrayList_InCurrentWeatherAdapter.get(position).thumbnail
        Glide.with(context_Instance_InCurrentWeatherAdapter).load(imageUrl_InMyViewHolder_InCurrentWeatherAdapter).into(holder.img_product_CurrentWeather)

        holder.tv_title_value_CurrentWeather.setText(productsArrayList_InCurrentWeatherAdapter.get(position).title)
        holder.tv_price_value_CurrentWeather.setText(productsArrayList_InCurrentWeatherAdapter.get(position).price.toString())
        holder.tv_descroption_value_CurrentWeather.setText(productsArrayList_InCurrentWeatherAdapter.get(position).description)
        holder.tv_id_value_CurrentWeather.setText(productsArrayList_InCurrentWeatherAdapter.get(position).id.toString())

        holder.btn_addToFavourite_CurrentWeather.setOnClickListener(){
            onCurrentWeatherClickListenerInterface_Instance_InWeatherAdapter.onClick_insertProductToFavouriteProductsActivity_InOnProductClickListenerInterface(productsArrayList_InCurrentWeatherAdapter.get(position))
        }

    }
}

 */

class CurrentWeatherAdapter: RecyclerView.Adapter<CurrentWeatherAdapter.MyViewHolder_InCurrentWeatherAdapter>  {


    private final var tab: String = "team"

    var context_Instance_InCurrentWeatherAdapter: Context
    var cod_InCurrentWeatherAdapter: String
    var message_InCurrentWeatherAdapter: Int
    var cnt_InCurrentWeatherAdapter: Int
    var weatherArrayList_InCurrentWeatherAdapter: ArrayList<Model_WeatherArrayList>
    var city_InCurrentWeatherAdapter: Model_City
  

    public constructor(context_Instance_ConstructorParameter_InCurrentWeatherAdapter: Context,
                       cod_Instance_ConstructorParameter_InCurrentWeatherAdapter:String,
                       message_Instance_ConstructorParameter_InCurrentWeatherAdapter: Int,
                       cnt_Instance_ConstructorParameter_InCurrentWeatherAdapter: Int,
                       weatherArrayList_ConstructorParameter_InCurrentWeatherAdapter: ArrayList<Model_WeatherArrayList>,
                       city_Instance_ConstructorParameter_InCurrentWeatherAdapter: Model_City){

        this.context_Instance_InCurrentWeatherAdapter=context_Instance_ConstructorParameter_InCurrentWeatherAdapter
        this.cod_InCurrentWeatherAdapter = cod_Instance_ConstructorParameter_InCurrentWeatherAdapter
        this.message_InCurrentWeatherAdapter = message_Instance_ConstructorParameter_InCurrentWeatherAdapter
        this.cnt_InCurrentWeatherAdapter = cnt_Instance_ConstructorParameter_InCurrentWeatherAdapter
        this.weatherArrayList_InCurrentWeatherAdapter=weatherArrayList_ConstructorParameter_InCurrentWeatherAdapter
        this.city_InCurrentWeatherAdapter = city_Instance_ConstructorParameter_InCurrentWeatherAdapter

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder_InCurrentWeatherAdapter {
        var inflater: LayoutInflater = context_Instance_InCurrentWeatherAdapter.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as (LayoutInflater)
        var view: View = inflater.inflate(R.layout.current_day_card,parent,false)
        var myViewHolder_InCurrentWeatherAdapter: MyViewHolder_InCurrentWeatherAdapter = MyViewHolder_InCurrentWeatherAdapter(view)
        return  myViewHolder_InCurrentWeatherAdapter
    }

    override fun getItemCount(): Int {
        return weatherArrayList_InCurrentWeatherAdapter.size
    }

    fun setWeatherArrayList_InCurrentWeatherAdapter(weaterArrayList: ArrayList<Model_WeatherArrayList>){
        Log.i("TAG", "setWeatherArrayList_InCurrentWeatherAdapter: WeatherArrayList :" + weaterArrayList)
        this.weatherArrayList_InCurrentWeatherAdapter = weaterArrayList
        notifyDataSetChanged()
    }

    fun setCod_InCurrentWeatherAdapter(cod: String){
        Log.i("TAG", "setCod_InCurrentWeatherAdapter: cod:  " + cod)
        cod_InCurrentWeatherAdapter = cod
        notifyDataSetChanged()
    }

    fun setMessage_InCurrentWeatherAdapter(message: Int){
        Log.i("TAG", "setMessage_InCurrentWeatherAdapter: message :" + message)
        message_InCurrentWeatherAdapter = message
        notifyDataSetChanged()
    }

    fun setCnt_InCurrentWeatherAdapter(cnt: Int){
        Log.i("TAG", "setCnt_InCurrentWeatherAdapter: cnt :" + cnt)
        cnt_InCurrentWeatherAdapter=cnt
        notifyDataSetChanged()
    }

    fun setCity_InCurrentWeatherAdapter(city: Model_City){
        Log.i("TAG", "setCity_InCurrentWeatherAdapter: city :" + city)
        city_InCurrentWeatherAdapter=city
        notifyDataSetChanged()
    }

    class MyViewHolder_InCurrentWeatherAdapter: RecyclerView.ViewHolder {

        var tv_time: TextView
        var img_weatherStatus: ImageView
        var tv_WeatherTempratureDegree: TextView

        constructor(@NonNull itemView: View): super(itemView){
            tv_time = itemView.findViewById(R.id.tv_time)
            tv_WeatherTempratureDegree = itemView.findViewById(R.id.tv_WeatherTempratureDegree)
            img_weatherStatus = itemView.findViewById(R.id.img_weatherStatus)

        }
    }

    override fun onBindViewHolder(holder: MyViewHolder_InCurrentWeatherAdapter, position: Int) {
        var imageUrl_InMyViewHolder_InCurrentWeatherAdapter: String = weatherArrayList_InCurrentWeatherAdapter.get(position).thumbnail
        Glide.with(context_Instance_InCurrentWeatherAdapter).load(imageUrl_InMyViewHolder_InCurrentWeatherAdapter).into(holder.img_product_CurrentWeather)

        holder.tv_title_value_CurrentWeather.setText(weatherArrayList_InCurrentWeatherAdapter.get(position).title)
        holder.tv_price_value_CurrentWeather.setText(weatherArrayList_InCurrentWeatherAdapter.get(position).price.toString())
        holder.tv_descroption_value_CurrentWeather.setText(weatherArrayList_InCurrentWeatherAdapter.get(position).description)
        holder.tv_id_value_CurrentWeather.setText(weatherArrayList_InCurrentWeatherAdapter.get(position).id.toString())
/*
        holder.btn_addToFavourite_CurrentWeather.setOnClickListener(){
            onCurrentWeatherClickListenerInterface_Instance_InWeatherAdapter.onClick_insertProductToFavouriteProductsActivity_InOnProductClickListenerInterface(weatherArrayList_InCurrentWeatherAdapter.get(position))
        }

 */

    }
}