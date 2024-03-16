package com.example.weatherapplication.CurrentWeather.CurrentWeatherView

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapplication.Model_WeatherArrayList
import com.example.weatherapplication.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.ArrayList

class CurrentWeatherAdapter_Day: RecyclerView.Adapter<CurrentWeatherAdapter_Day.MyViewHolder_InCurrentWeatherAdapter_Day> {


    private final var tab: String = "team"

    var context_Instance_InCurrentWeatherAdapter_Day: Context
    var weatherArrayList_InCurrentWeatherAdapter_Day: ArrayList<Model_WeatherArrayList>


    public constructor(
        context_Instance_ConstructorParameter_InCurrentWeatherAdapter_Day: Context,
        weatherArrayList_ConstructorParameter_InCurrentWeatherAdapter_Day: ArrayList<Model_WeatherArrayList>,
    ) {
        this.context_Instance_InCurrentWeatherAdapter_Day =
            context_Instance_ConstructorParameter_InCurrentWeatherAdapter_Day
        this.weatherArrayList_InCurrentWeatherAdapter_Day =
            weatherArrayList_ConstructorParameter_InCurrentWeatherAdapter_Day
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder_InCurrentWeatherAdapter_Day {
        var inflater: LayoutInflater =
            context_Instance_InCurrentWeatherAdapter_Day.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
            ) as (LayoutInflater)
        var view: View = inflater.inflate(R.layout.card_hom_day, parent, false)
        var myViewHolder_InCurrentWeatherAdapterDay: MyViewHolder_InCurrentWeatherAdapter_Day =
            MyViewHolder_InCurrentWeatherAdapter_Day(view)
        return myViewHolder_InCurrentWeatherAdapterDay
    }

    override fun getItemCount(): Int {
        return weatherArrayList_InCurrentWeatherAdapter_Day.size
    }

    fun settingWeatherArrayList_InCurrentWeatherAdapter(weatherArrayList: ArrayList<Model_WeatherArrayList>) {
        Log.i(
            "TAG",
            "settingWeatherArrayList_InCurrentWeatherAdapter_Day: WeatherArrayList :" + weatherArrayList
        )
        this.weatherArrayList_InCurrentWeatherAdapter_Day = weatherArrayList
        notifyDataSetChanged()
    }

    class MyViewHolder_InCurrentWeatherAdapter_Day : RecyclerView.ViewHolder {

        var tv_day: TextView
        var img_day: ImageView
        var tv_description: TextView
        var tv_minTemp: TextView
        var tv_maxTemp: TextView


        constructor(@NonNull itemView: View) : super(itemView) {
            tv_day = itemView.findViewById(R.id.tv_day)
            tv_description = itemView.findViewById(R.id.tv_description)
            tv_minTemp = itemView.findViewById(R.id.tv_Minemp)
            img_day = itemView.findViewById(R.id.img_day)
            tv_maxTemp = itemView.findViewById(R.id.tv_maxTemp)

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder_InCurrentWeatherAdapter_Day, position: Int) {


        var imageIconCode: String? =
            weatherArrayList_InCurrentWeatherAdapter_Day.get(position + 1).modelWeather.get(0).icon
        var imageUrl_InMyViewHolder_InCurrentWeatherAdapter_Day: String? =
            "https://openweathermap.org/img/wn/$imageIconCode@2x.png"
        Glide.with(context_Instance_InCurrentWeatherAdapter_Day)
            .load(imageUrl_InMyViewHolder_InCurrentWeatherAdapter_Day).into(holder.img_day)


        var dateAndTimeFromWeatherArrayList =
            weatherArrayList_InCurrentWeatherAdapter_Day.get(position + 1).dtTxt?.split(" ")

        var dtTxt = weatherArrayList_InCurrentWeatherAdapter_Day.get(position + 1).dtTxt

        val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val date = LocalDate.parse(dtTxt , firstApiFormat)

        holder.tv_day.setText(date.dayOfWeek.toString())


        holder.tv_description.setText(
            weatherArrayList_InCurrentWeatherAdapter_Day.get(position + 1).modelWeather.get(
                0
            ).description
        )

        var minTemp =
            weatherArrayList_InCurrentWeatherAdapter_Day.get(position + 1).modelMain?.tempMin.toString()
        var maxTemp =
            weatherArrayList_InCurrentWeatherAdapter_Day.get(position + 1).modelMain?.tempMax.toString()

        holder.tv_minTemp.setText("$minTemp°C")
        holder.tv_maxTemp.setText("$maxTemp°C")


    }
}