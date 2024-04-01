package com.example.weatherapplication.SharedPreferences

import android.content.Context
import android.content.SharedPreferences
import com.example.weatherapplication.Constants.Utils

//to load the current weather not the city weather
class SharedPrefrences internal constructor(private val context: Context){

    companion object{
        private const val SHARED_PREFS_NAME = "my_prefs"
        private const val KEY_CITY = "city"

        private var instance: SharedPrefrences? = null
        fun getInstance(context:Context): SharedPrefrences {
            if(instance==null){
                instance = SharedPrefrences(context.applicationContext)
            }
            return instance!!
        }
    }


    private  val prefs: SharedPreferences by lazy{
        context.getSharedPreferences(SHARED_PREFS_NAME,Context.MODE_PRIVATE)
    }

    fun setValue(key: String, value: String){
        prefs.edit().putString(key,value)
    }

    fun getValue(key: String): String?{
        return prefs.getString(key,null)
    }
    //--------------------
    fun setLocationValue(key: String, value: String){
        prefs.edit().putString(key,value)
    }
    fun getLocationValue(key: String): String?{
        return prefs.getString(key,null)
    }

    //--------------
    fun setLanguageValue(key: String, value: String){
        prefs.edit().putString(key,value)
    }
    fun getLanguageValue(key: String): String?{
        return prefs.getString(key,null)
    }


    //--------------------
    fun setTempratureValue(key: String, value: String){
        prefs.edit().putString(key,value)
    }
    fun getTempratureValue(key: String): String?{
        return prefs.getString(key,null)
    }
    //--------------------


    //--------------------
    fun setNotificationValue(key: String, value: String){
        prefs.edit().putString(key,value)
    }
    fun getNotificationValue(key: String): String?{
        return prefs.getString(key,null)
    }
    //--------------------


    fun setValueOrNull(key: String? , value: String?){
        if(key!=null && value!=null){
            prefs.edit().putString(key,value).apply()
        }
    }

    fun getValueOrNull(key: String?): String? {
        if(key!=null){
            return prefs.getString(key,null)
        }
        return null
    }

    //clear the sharedPreferences
    fun clearCityValue(){
        prefs.edit().remove(KEY_CITY).apply()
    }


}