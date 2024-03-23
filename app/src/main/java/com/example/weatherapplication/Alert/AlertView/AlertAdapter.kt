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
import com.example.weatherapplication.Model.Model_Time
import com.example.weatherapplication.R
import java.util.Calendar
import java.util.Date

class AlertAdapter: RecyclerView.Adapter<AlertAdapter.MyViewHolder_InAlertAdapter> {


    private final var tab: String = "team"

    var context_Instance_InAlertAdapter: Context
    var timeArrayList_InAlertAdapter: ArrayList<Model_Time>


    public constructor(
        context_Instance_ConstructorParameter_InAlertAdapter: Context,
        timeArrayList_ConstructorParameter_InAlertAdapter: ArrayList<Model_Time>,
    ) {
        this.context_Instance_InAlertAdapter =
            context_Instance_ConstructorParameter_InAlertAdapter
        this.timeArrayList_InAlertAdapter =
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
        return timeArrayList_InAlertAdapter.size
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


    fun addNotification(selectedDateTime: Date) {
        val calendar = Calendar.getInstance()
        calendar.time = selectedDateTime

        val modelTime = Model_Time(
            year = calendar.get(Calendar.YEAR).toString(),
            month = calendar.get(Calendar.MONTH).toString(),
            day = calendar.get(Calendar.DAY_OF_MONTH).toString(),
            hour = calendar.get(Calendar.HOUR_OF_DAY).toString(),
            minutes = calendar.get(Calendar.MINUTE).toString()
        )

        timeArrayList_InAlertAdapter.add(modelTime)
        notifyDataSetChanged()
    }

    class MyViewHolder_InAlertAdapter : RecyclerView.ViewHolder {

        var tv_day: TextView
        var tv_hour: TextView
        var tv_minute: TextView
        var tv_month: TextView


        constructor(@NonNull itemView: View) : super(itemView) {
            tv_day = itemView.findViewById(R.id.tv_day_alert)
            tv_hour = itemView.findViewById(R.id.tv_hour_alert)
            tv_minute = itemView.findViewById(R.id.tv_minute_alert)
            tv_month = itemView.findViewById(R.id.tv_month_alert)

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder_InAlertAdapter, position: Int) {

        Log.i("TAG", "onBindViewHolder: Day Adapter: position = " +position)

        holder.tv_hour.setText(timeArrayList_InAlertAdapter.get(position).hour)
        holder.tv_month.setText(timeArrayList_InAlertAdapter.get(position).month)
        holder.tv_day.setText(timeArrayList_InAlertAdapter.get(position).day)
        holder.tv_minute.setText(timeArrayList_InAlertAdapter.get(position).minutes)

    }
}