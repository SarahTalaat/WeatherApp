package com.example.weatherapplication.Alert.AlertView

import AlertFragment
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.productsmvvm.FavouriteProducts.FavouriteProductsView.OnAlertClickListenerInterface
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import com.example.weatherapplication.R

class AlertAdapter: RecyclerView.Adapter<AlertAdapter.MyViewHolder_InAlertAdapter> {


    private final var tab: String = "team"

    var context_Instance_InAlertAdapter: Context
    var modelTimeArrayList_InAlertAdapter: ArrayList<Model_Time>
    var onAlertClickListenerInterface: OnAlertClickListenerInterface


    public constructor(
        context_Instance_ConstructorParameter_InAlertAdapter: Context,
        timeArrayList_ConstructorParameter_InAlertAdapter: ArrayList<Model_Time>,
        onAlertClickListenerInterface: OnAlertClickListenerInterface
    ) {
        this.context_Instance_InAlertAdapter =
            context_Instance_ConstructorParameter_InAlertAdapter
        this.modelTimeArrayList_InAlertAdapter =
            timeArrayList_ConstructorParameter_InAlertAdapter
        this.onAlertClickListenerInterface=onAlertClickListenerInterface
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
    fun set*tingTimeArrayList_InAlertAdapter(timeModel: Model_Time) {
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


    }
    fun setModelTimeList_InAlertAdapter(modelTimeArrayList: java.util.ArrayList<Model_Time>){
        Log.i("TAG", "setModelTimeList_InModelTimeAdapter: modelTimeArrayList :" + modelTimeArrayList)

        var model_Time = AlertFragment.getInstance().model_Time_Instance
        if(model_Time.city!= "nullValue" &&
           model_Time.specificTime != "nullValue" &&
           model_Time.endDate !="nullValue" &&
           model_Time.latitude !="nullValue" &&
           model_Time.startDate != "nullValue" &&
           model_Time.longitude != "nullValue" &&
            AlertFragment.getInstance().shallCardAppear == true){

            this.modelTimeArrayList_InAlertAdapter = modelTimeArrayList
            notifyDataSetChanged()
        }
    }

    class MyViewHolder_InAlertAdapter : RecyclerView.ViewHolder {

        var tv_specficTime: TextView
        var tv_startDate: TextView
        var tv_city: TextView
        var tv_endDate: TextView
        var btn_delete: Button


        constructor(@NonNull itemView: View) : super(itemView) {
            tv_specficTime = itemView.findViewById(R.id.tv_specificTime_alert)
            tv_startDate = itemView.findViewById(R.id.tv_startDate_alert)
            tv_city = itemView.findViewById(R.id.tv_city_alert)
            tv_endDate = itemView.findViewById(R.id.tv_endDate_alert)
            btn_delete = itemView.findViewById(R.id.btn_delete_alert)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder_InAlertAdapter, position: Int) {

        Log.i("TAG", "onBindViewHolder: Day Adapter: position = " +position)

        holder.tv_startDate.setText(modelTimeArrayList_InAlertAdapter.get(position).startDate)
        holder.tv_endDate.setText(modelTimeArrayList_InAlertAdapter.get(position).endDate)
        holder.tv_specficTime.setText(modelTimeArrayList_InAlertAdapter.get(position).specificTime)
        holder.tv_city.setText(modelTimeArrayList_InAlertAdapter.get(position).city)
        holder.btn_delete.setOnClickListener(){
            var model_Time = modelTimeArrayList_InAlertAdapter.get(position)
            onAlertClickListenerInterface.onClick_DeleteModelTimeFromAlertFragment_InOnAlertClickListenerInterface(model_Time)
        }
    }
}