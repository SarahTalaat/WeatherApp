package com.example.weatherapplication.Constants

class Utils {

    companion object{
        var API_KEY: String = "02f7303416defaa054fd9589e2bd7ce2"
        const val PERMISSION_REQUEST_CODE = 123
        var FAVOURITE_CITY_KEY = "favourite_city_key"
        var FAVOURITE_CITY_VALUE = "favourite_city_value"
        var FAVOURITE_CITY_LATITUDE = "favourite_city_latitude"
        var FAVOURITE_CITY_LONGITUDE = "favourite_city_longitude"
        var FAVOURITE_CITY_NAME = "favourite_city_name"
        var LAT_EGYPT = "26.8206"
        var LON_EGYPT = "30.8025"
     //   var LAT_ALERT = "56.1304"
     //   var lON_ALERT = "106.3468"
        var LAT_ALERT = "46.8182"
        var lON_ALERT = "8.2275"
        var ALERT_DATA_SP = "AlertData"
        var MODEL_ALERT_GSON ="modelAlert"
        var DISMISS_NOTIFICATION = "DISMISS_NOTIFICATION"
        var STOP_NOTIFICATION = "STOP_NOTIFICATION"
        var NOTIFICATION_KEY = "NOTIFICATION_KEY"

        //lat=56.1304&lon=106.3468


        const val ALARM_REQUEST_CODE = 1001
        const val REQUEST_DRAW_OVER_APPS_PERMISSION = 1002
        const val CHANNEL_ID = "my_channel_id"
        var NOTIFICATION_ID = 2001 // Unique identifier for notifications
        const val NOTIFICATION_PERMISSION_REQUEST_CODE = 2002

        const val STOP_NOTIFICATION_REQUEST_CODE = 2003
        const val NOTIFICATION_ID_EXTRA = "notification_id_extra"
        const val DISMISS_NOTIFICATION_REQUEST_CODE = 101


        var ISNOTIFICATION = "ISNOTIFICATION"
        var SPECIFICTIME = "SPECIFICTIME"




    }

}