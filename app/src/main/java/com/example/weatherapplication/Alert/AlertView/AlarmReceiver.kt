package com.example.myweatherapp


import android.Manifest
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.weatherapplication.Constants.Utils
import com.example.weatherapplication.Model_Alert
import com.example.weatherapplication.R
import com.google.gson.Gson


class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        // Check if context is not null
        if (context != null) {
            // Create a notification channel

            val sharedPreferences = context?.getSharedPreferences(Utils.ALERT_DATA_SP, Context.MODE_PRIVATE)
            val modelAlertJson = sharedPreferences?.getString(Utils.MODEL_ALERT_GSON, null)

            if (modelAlertJson != null) {
                val gson = Gson()
                val modelAlert = gson.fromJson(modelAlertJson, Model_Alert::class.java)
                // Now you have your Model_Alert object

                if (modelAlert.alerts.isNotEmpty()){
                    notification(context,"Dangerous Situation" , "${modelAlert.alerts.get(0).description}")
                }else {
                    notification(context,"The weather is fine" , "Enjoy your day!!")

                }

            } else {
                Toast.makeText(context, "The json is null", Toast.LENGTH_SHORT).show()
                Log.i("TAG", "onReceive: AlarmReceiver Alart , The json is null ")
            }






/*
            createNotificationChannel(context)
            Toast.makeText(context, "context in alarm receiver is not null", Toast.LENGTH_SHORT).show()

            // Create a notification
            val builder = NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Alarm Triggered")
                .setContentText("Time to wake up!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            // Show the notification
            with(NotificationManagerCompat.from(context)) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                notify(Constants.NOTIFICATION_ID, builder.build())
            }
            */
        }
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Alarm Notifications"
            val descriptionText = "Channel for alarm notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(Constants.CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun notification(context: Context , title: String , contentText: String){
        createNotificationChannel(context)
        Toast.makeText(context, "context in alarm receiver is not null", Toast.LENGTH_SHORT).show()

        // Create a notification
        val builder = NotificationCompat.Builder(context, Constants.CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(title)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        // Show the notification
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(Constants.NOTIFICATION_ID, builder.build())
        }
    }
    }
