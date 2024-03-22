package com.example.weatherapplication.Alert.AlertView

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

class AlertAdapter: RecyclerView.Adapter<AlertAdapter.MyViewHolder_InAlertAdapter> {


    private final var tab: String = "team"

    var context_Instance_InAlertAdapter: Context
    var weatherArrayList_InAlertAdapter: ArrayList<Model_WeatherArrayList>


    public constructor(
        context_Instance_ConstructorParameter_InAlertAdapter: Context,
        weatherArrayList_ConstructorParameter_InAlertAdapter: ArrayList<Model_WeatherArrayList>,
    ) {
        this.context_Instance_InAlertAdapter =
            context_Instance_ConstructorParameter_InAlertAdapter
        this.weatherArrayList_InAlertAdapter =
            weatherArrayList_ConstructorParameter_InAlertAdapter
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder_InAlertAdapter {
        var inflater: LayoutInflater =
            context_Instance_InAlertAdapter.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
            ) as (LayoutInflater)
        var view: View = inflater.inflate(R.layout.card_home_day, parent, false)
        var myViewHolder_InAlertAdapterDay: MyViewHolder_InAlertAdapter =
            MyViewHolder_InAlertAdapter(view)
        return myViewHolder_InAlertAdapterDay
    }

    override fun getItemCount(): Int {
        return weatherArrayList_InAlertAdapter.size
    }

    fun settingWeatherArrayList_InAlertAdapter(weatherArrayList: ArrayList<Model_WeatherArrayList>) {
        Log.i(
            "TAG",
            "settingWeatherArrayList_InAlertAdapter: WeatherArrayList :" + weatherArrayList
        )
/*
        for(i in 0 until weatherArrayList.size){

            var dtTxt_value = weatherArrayList.get(i).dtTxt

            var dateAndTimeFromWeatherArrayList =
                dtTxt_value?.split(" ")
            var timeFromDateAndTimeArrayList = dateAndTimeFromWeatherArrayList?.get(1)

            if(timeFromDateAndTimeArrayList == "03:00:00"){
                this.weatherArrayList_InAlertAdapter.add(weatherArrayList.get(i))
                Log.i("TAG", "settingWeatherArrayList_InAlertAdapter: Day adapter: i= "+i)
                Log.i("TAG", "settingWeatherArrayList_InAlertAdapter: Day adapter: dateAndTimeFromWeatherArrayList?.get(1): "+ dateAndTimeFromWeatherArrayList?.get(1) )
                Log.i("TAG", "settingWeatherArrayList_InAlertAdapter: Day adapter: dateAndTimeFromWeatherArrayList?.get(0): "+ dateAndTimeFromWeatherArrayList?.get(0) )

            }
        }

 */
        this.weatherArrayList_InAlertAdapter = setUniqueDayArrayList(weatherArrayList)
        notifyDataSetChanged()
    }

    fun setUniqueDayArrayList(weatherArrayList: ArrayList<Model_WeatherArrayList>) : ArrayList<Model_WeatherArrayList>{
        val uniqueDayHashSet = HashSet<String?>()
        val filterList = weatherArrayList.filter { entry ->

            val day_FromDtTxt = entry.dtTxt?.split(" ")?.get(0)
            if(uniqueDayHashSet.contains(day_FromDtTxt)){
                false
            }else{
                uniqueDayHashSet.add(day_FromDtTxt)
                true
            }
        }
        return  filterList as ArrayList<Model_WeatherArrayList>
    }


    class MyViewHolder_InAlertAdapter : RecyclerView.ViewHolder {

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
    override fun onBindViewHolder(holder: MyViewHolder_InAlertAdapter, position: Int) {


        Log.i("TAG", "onBindViewHolder: Day Adapter: position = " +position)

        var dateAndTimeFromWeatherArrayList =
            weatherArrayList_InAlertAdapter.get(position).dtTxt?.split(" ")
        var dayToBeShownBasedOnTime = dateAndTimeFromWeatherArrayList?.get(1)
        Log.i("TAG", "onBindViewHolder: Day adapter: dateAndTimeFromWeatherArrayList?.get(1): "+ dateAndTimeFromWeatherArrayList?.get(1) )
        Log.i("TAG", "onBindViewHolder: Day adapter: dateAndTimeFromWeatherArrayList?.get(0): "+ dateAndTimeFromWeatherArrayList?.get(0) )



            var imageIconCode: String? =
                weatherArrayList_InAlertAdapter.get(position).modelWeather.get(0).icon
            var imageUrl_InMyViewHolder_InAlertAdapter: String? =
                "https://openweathermap.org/img/wn/$imageIconCode@2x.png"
            Glide.with(context_Instance_InAlertAdapter)
                .load(imageUrl_InMyViewHolder_InAlertAdapter).into(holder.img_day)

            var dtTxt = weatherArrayList_InAlertAdapter.get(position).dtTxt

            val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val date = LocalDate.parse(dtTxt , firstApiFormat)

            holder.tv_day.setText(date.dayOfWeek.toString())

            holder.tv_description.setText(
                weatherArrayList_InAlertAdapter.get(position).modelWeather.get(
                    0
                ).description
            )

            var minTemp =
                weatherArrayList_InAlertAdapter.get(position).modelMain?.tempMin.toString()
            var maxTemp =
                weatherArrayList_InAlertAdapter.get(position).modelMain?.tempMax.toString()

        var tempratureFehrenheit_minTemp = weatherArrayList_InAlertAdapter.get(position).modelMain?.tempMin
        var tempratureCelsius_minTemp = tempratureFehrenheit_minTemp?.minus(273.15)
        var tempFormated_minTemp = String.format("%.2f", tempratureCelsius_minTemp)

        var tempratureFehrenheit_maxTemp = weatherArrayList_InAlertAdapter.get(position).modelMain?.tempMax
        var tempratureCelsius_maxTemp = tempratureFehrenheit_maxTemp?.minus(273.15)
        var tempFormated_maxTemp = String.format("%.2f", tempratureCelsius_maxTemp)



            holder.tv_minTemp.setText("$tempFormated_minTemp°C")
            holder.tv_maxTemp.setText("$tempFormated_maxTemp°C")




    }
}