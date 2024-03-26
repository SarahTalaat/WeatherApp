package com.example.weatherapplication.Alert.AlertView

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import com.example.weatherapplication.R

class AlertAdapter: RecyclerView.Adapter<AlertAdapter.MyViewHolder_InAlertAdapter> {


    private final var tab: String = "team"

    var context_Instance_InAlertAdapter: Context
    var modelTimeArrayList_InAlertAdapter: ArrayList<Model_Time>


    public constructor(
        context_Instance_ConstructorParameter_InAlertAdapter: Context,
        timeArrayList_ConstructorParameter_InAlertAdapter: ArrayList<Model_Time>,
    ) {
        this.context_Instance_InAlertAdapter =
            context_Instance_ConstructorParameter_InAlertAdapter
        this.modelTimeArrayList_InAlertAdapter =
            timeArrayList_ConstructorParameter_InAlertAdapter
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder_InAlertAdapter {
        var inflater: LayoutInflater =
            context_Instance_InAlertAdapter.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
            ) as (LayoutInflater)
        var view: View = inflater.inflate(R.layout.card_alert, parent, false)
        var myViewHolder_InAlertAdapterDay: MyViewHolder_InAlertAdapter =
            MyViewHolder_InAlertAdapter(view)
        return myViewHolder_InAlertAdapterDay
    }

    override fun getItemCount(): Int {
        return modelTimeArrayList_InAlertAdapter.size
    }



/*
    fun settingTimeArrayList_InAlertAdapter(timeModel: Model_Time) {
        Log.i(
            "TAG",
            "settingTimeArrayList_InAlertAdapter: TimeArrayList :" + timeModel
        )
        this.timeArrayList_InAlertAdapter.add(timeModel)
        notifyDataSetChanged()
    }
    */



    fun receiveodelTimeInAlertAdapter(model_Time: Model_Time) {

        Log.i("TAG", "receiveodelTimeInAlertAdapter: StartDte: ${model_Time.startDate}")
        Log.i("TAG", "receiveodelTimeInAlertAdapter: StartDte: ${model_Time.endDate}")
        Log.i("TAG", "receiveodelTimeInAlertAdapter: StartDte: ${model_Time.specificTime}")
        Log.i("TAG", "receiveodelTimeInAlertAdapter: StartDte: ${model_Time.city}")
        Log.i("TAG", "receiveodelTimeInAlertAdapter: StartDte: ${model_Time.latitude}")
        Log.i("TAG", "receiveodelTimeInAlertAdapter: StartDte: ${model_Time.longitude}")

       if(model_Time.startDate != "nullValue" &&
          model_Time.endDate != "nullValue" &&
          model_Time.specificTime != "nullValue" &&
          model_Time.latitude != "nullValue" &&
          model_Time.longitude != "nullValue" &&
          model_Time.city != "nullValue"){
            modelTimeArrayList_InAlertAdapter.add(model_Time)
            notifyDataSetChanged()
       }


    fun addNotification(timeModel :Model_Time) {

        timeArrayList_InAlertAdapter.add(timeModel)
        notifyDataSetChanged()

        Log.d("AlertAdapter", "Added notification: $timeModel")
        Log.d("AlertAdapter", "Size of data list: ${timeArrayList_InAlertAdapter.size}")

    }

    class MyViewHolder_InAlertAdapter : RecyclerView.ViewHolder {

        var tv_specficTime: TextView
        var tv_startDate: TextView
        var tv_city: TextView
        var tv_endDate: TextView


        constructor(@NonNull itemView: View) : super(itemView) {
            tv_specficTime = itemView.findViewById(R.id.tv_specificTime_alert)
            tv_startDate = itemView.findViewById(R.id.tv_startDate_alert)
            tv_city = itemView.findViewById(R.id.tv_city_alert)
            tv_endDate = itemView.findViewById(R.id.tv_endDate_alert)

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder_InAlertAdapter, position: Int) {

        Log.i("TAG", "onBindViewHolder: Day Adapter: position = " +position)


        holder.tv_startDate.setText(modelTimeArrayList_InAlertAdapter.get(position).startDate)
        holder.tv_endDate.setText(modelTimeArrayList_InAlertAdapter.get(position).endDate)
        holder.tv_specficTime.setText(modelTimeArrayList_InAlertAdapter.get(position).specificTime)
        holder.tv_city.setText(modelTimeArrayList_InAlertAdapter.get(position).city)

        holder.tv_hour.setText(timeArrayList_InAlertAdapter.get(position).startDate)
        holder.tv_month.setText(timeArrayList_InAlertAdapter.get(position).endDate)
        holder.tv_day.setText(timeArrayList_InAlertAdapter.get(position).specificTime)


    }
}