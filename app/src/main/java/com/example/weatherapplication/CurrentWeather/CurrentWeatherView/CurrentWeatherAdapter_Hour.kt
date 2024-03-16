package com.example.productsmvvm.CurrentWeather.CurrentWeatherView

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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

class CurrentWeatherAdapter_Hour: RecyclerView.Adapter<CurrentWeatherAdapter_Hour.MyViewHolder_InCurrentWeatherAdapter_Hour>  {


    private final var tab: String = "team"

    var context_Instance_InCurrentWeatherAdapter: Context
    var weatherArrayList_InCurrentWeatherAdapter: ArrayList<Model_WeatherArrayList>
    var timeListSplit: List<String>? = listOf()
  

    public constructor(context_Instance_ConstructorParameter_InCurrentWeatherAdapter: Context,
                       weatherArrayList_ConstructorParameter_InCurrentWeatherAdapter: ArrayList<Model_WeatherArrayList>,
                       ){
        this.context_Instance_InCurrentWeatherAdapter = context_Instance_ConstructorParameter_InCurrentWeatherAdapter
        this.weatherArrayList_InCurrentWeatherAdapter = weatherArrayList_ConstructorParameter_InCurrentWeatherAdapter
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder_InCurrentWeatherAdapter_Hour {
        var inflater: LayoutInflater = context_Instance_InCurrentWeatherAdapter.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as (LayoutInflater)
        var view: View = inflater.inflate(R.layout.card_home_hour,parent,false)
        var myViewHolder_InCurrentWeatherAdapterHor: MyViewHolder_InCurrentWeatherAdapter_Hour = MyViewHolder_InCurrentWeatherAdapter_Hour(view)
        return  myViewHolder_InCurrentWeatherAdapterHor
    }

    override fun getItemCount(): Int {
        return weatherArrayList_InCurrentWeatherAdapter.size
    }

    fun settingWeatherArrayList_InCurrentWeatherAdapter(weatherArrayList: ArrayList<Model_WeatherArrayList>){
        Log.i("TAG", "settingWeatherArrayList_InCurrentWeatherAdapter: WeatherArrayList :" + weatherArrayList)
        var firstFourElementsOfweatherArrayList: List<Model_WeatherArrayList> = weatherArrayList.take(4)
        this.weatherArrayList_InCurrentWeatherAdapter = firstFourElementsOfweatherArrayList as ArrayList<Model_WeatherArrayList>
        notifyDataSetChanged()
    }

    class MyViewHolder_InCurrentWeatherAdapter_Hour: RecyclerView.ViewHolder {

        var tv_time: TextView
        var img_weatherStatus: ImageView
        var tv_WeatherTempratureDegree: TextView

        constructor(@NonNull itemView: View): super(itemView){
            tv_time = itemView.findViewById(R.id.tv_time)
            tv_WeatherTempratureDegree = itemView.findViewById(R.id.tv_WeatherTempratureDegree)
            img_weatherStatus = itemView.findViewById(R.id.img_weatherStatus)

        }
    }

    override fun onBindViewHolder(holder: MyViewHolder_InCurrentWeatherAdapter_Hour, position: Int) {


            timeListSplit =weatherArrayList_InCurrentWeatherAdapter.get(position).dtTxt?.split(" ")
            var imageIconCode: String? = weatherArrayList_InCurrentWeatherAdapter.get(position).modelWeather.get(0).icon
            var imageUrl_InMyViewHolder_InCurrentWeatherAdapter: String? = "https://openweathermap.org/img/wn/$imageIconCode@2x.png"
            Glide.with(context_Instance_InCurrentWeatherAdapter).load(imageUrl_InMyViewHolder_InCurrentWeatherAdapter).into(holder.img_weatherStatus)
            var hourText = timeListSplit?.get(1)
            if(hourText!= null){
                when(hourText){
                    "00:00:00" -> holder.tv_time.setText("12 pm")
                    "03:00:00" -> holder.tv_time.setText("3 pm")
                    "06:00:00" -> holder.tv_time.setText("6 pm")
                    "09:00:00" -> holder.tv_time.setText("9 pm")
                }
            }

            var tempratureFehrenheit = weatherArrayList_InCurrentWeatherAdapter.get(0).modelMain?.temp
            var tempratureCelsius = tempratureFehrenheit?.minus(273.15)
            val tempFormated = String.format("%.2f", tempratureCelsius)

            holder.tv_WeatherTempratureDegree.setText(tempFormated + "°C")



/*
        holder.btn_addToFavourite_CurrentWeather.setOnClickListener(){
            onCurrentWeatherClickListenerInterface_Instance_InWeatherAdapter.onClick_insertProductToFavouriteProductsActivity_InOnProductClickListenerInterface(weatherArrayList_InCurrentWeatherAdapter.get(position))
        }

 */

    }
}