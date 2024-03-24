package com.example.weatherapplication.Alert.AlertView

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.weatherapplication.Constants.Utils
import com.example.weatherapplication.Model.AlertModel.APIModel.Model_Alert
import com.example.weatherapplication.R
import com.google.gson.Gson

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        Log.i("TAG", "onReceive: AlarmReceiver: context = $context ")
        Log.i("TAG", "onReceive: AlarmReceiver: intent.action = ${intent?.action} ")

        if (context != null && intent != null) {
            val action = intent.action
            if (action != null && action == Utils.STOP_NOTIFICATION) {
                stopMediaPlayer(context)
                return
            }else if(action != null && action == Utils.DISMISS_NOTIFICATION){
                context?.let {
                    MediaPlayerSingleton.stop()
                    NotificationManagerCompat.from(it).cancel(Utils.NOTIFICATION_ID)
                }
                return
            }

            val sharedPreferences = context.getSharedPreferences(
                Utils.ALERT_DATA_SP,
                Context.MODE_PRIVATE
            )
            val modelAlertJson = sharedPreferences?.getString(Utils.MODEL_ALERT_GSON, null)

            if (modelAlertJson != null) {
                val gson = Gson()
                val modelAlert = gson.fromJson(modelAlertJson, Model_Alert::class.java)

                if (modelAlert.alerts.isNotEmpty()) {
                    notification(
                        context,
                        "Dangerous Situation",
                        "${modelAlert.alerts[0].description}"
                    )
                } else {
                    notification(context, "The weather is fine", "Enjoy your day!!")
                }
            } else {
                Toast.makeText(context, "The json is null", Toast.LENGTH_SHORT).show()
                Log.i("TAG", "onReceive: AlarmReceiver Alart , The json is null ")
            }
        }
    }

    private fun stopMediaPlayer(context: Context) {
        // Stop the media player here
        MediaPlayerSingleton.stop()
        Toast.makeText(context, "Media player stopped", Toast.LENGTH_SHORT).show()
    }


    private fun notification(context: Context, title: String, contentText: String) {
        createNotificationChannel(context)

        // Create a notification with dismiss and stop music actions
        val builder = NotificationCompat.Builder(context, Utils.CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon_blue)
            .setContentTitle(title)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(
                R.drawable.stop_music_icon,
                "Stop Music",
                getPendingIntentForStopNotification(context)
            )
            .addAction(
                R.drawable.dismiss_icon,
                "Dismiss",
                getPendingIntentForDismissNotification(context)
            )

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
            notify(Utils.NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationChannel(context: Context) {
        // Create a notification channel if not exists
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val name = "Alarm Notifications"
            val descriptionText = "Channel for alarm notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(
                Utils.CHANNEL_ID,
                name,
                importance
            ).apply {
                description = descriptionText
            }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun getPendingIntentForStopNotification(context: Context): PendingIntent {
        // Create an intent to stop the notification
        val stopIntent = Intent(context, AlarmReceiver::class.java).apply {
            action = Utils.STOP_NOTIFICATION
        }
        return PendingIntent.getBroadcast(
            context,
            Utils.STOP_NOTIFICATION_REQUEST_CODE,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

    }

    private fun getPendingIntentForDismissNotification(context: Context): PendingIntent {
        // Create an intent to dismiss the notification
        val dismissIntent = Intent(context, AlarmReceiver::class.java).apply {
            action = Utils.DISMISS_NOTIFICATION
        }
        return PendingIntent.getBroadcast(
            context,
            Utils.DISMISS_NOTIFICATION_REQUEST_CODE,
            dismissIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }


}

