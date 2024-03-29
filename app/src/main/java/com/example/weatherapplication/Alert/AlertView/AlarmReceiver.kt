package com.example.weatherapplication.Alert.AlertView

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
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


        val action = intent?.action
        if (action != null) {
            when (action) {
                Utils.STOP_NOTIFICATION -> {
                    stopMediaPlayerMusic(context)
                }
                Utils.DISMISS_NOTIFICATION -> {
                    stopMediaPlayerMusic(context)
                    dismissNotification(context)
                }
            }
        }

        val retrievedValue = intent?.getStringExtra(Utils.NOTIFICATION_KEY)

        Log.i("TAG", "onReceive: retrievedValue: $retrievedValue")


        if(retrievedValue== "true"){
            notificationLogic(context, intent)
            intent.removeExtra(Utils.NOTIFICATION_KEY)
        }else if(retrievedValue == "false"){
            alarmLogic(context, intent)
            intent.removeExtra(Utils.NOTIFICATION_KEY)
        }else{
            Log.i("TAG", "onReceive: No true or false value on the intent")
        }   

    }
    // Call this method where you initialize your app or set up your notification functionality
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My Notification Channel"
            val descriptionText = "Channel description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                Utils.CHANNEL_ID,
                name,
                importance
            ).apply {
                description = descriptionText
            }

            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun alarmLogic(context:Context?, intent: Intent?){

    if (context != null && intent != null) {

            MediaPlayerSingleton.getInstance(context).start()

        val action = intent.action
        if (action != null && action == Utils.STOP_NOTIFICATION) {

            stopMediaPlayerMusic(context)

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
        Log.i("TAG", "notificationLogic: modelAlertJson: $modelAlertJson")

        if (modelAlertJson != null) {
            val gson = Gson()
            val modelAlert = gson.fromJson(modelAlertJson, Model_Alert::class.java)
            if (modelAlert.alerts.isNotEmpty()) {
                showAlarm(
                    context,
                    "Dangerous Situation",
                    "${modelAlert.alerts[0].description}"
                )
            } else {
                showAlarm(context, "The weather is fine", "Enjoy your day!!")
            }
        } else {
            Toast.makeText(context, "The json is null", Toast.LENGTH_SHORT).show()
            Log.i("TAG", "onReceive: AlarmReceiver Alart , The json is null ")
        }
    }

}
    private fun showAlarm(context: Context, title: String, contentText: String) {
        createAlarmChannel(context)

        val soundUri: Uri? = null

        // Create a custom layout for the notification window
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 50 // Adjust as needed
            y = 50 // Adjust as needed
        }

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.custom_alam_dialog, null)

        // Set the title and content text
        view.findViewById<TextView>(R.id.titleTextView).text = title
        view.findViewById<TextView>(R.id.contentTextView).text = contentText

        // Set up the stop music button
        view.findViewById<Button>(R.id.stopMusicButton).setOnClickListener {
            // Perform actions to stop music
            stopMediaPlayerMusic(context)
        }

        // Set up the dismiss button
        view.findViewById<Button>(R.id.dismissButton).setOnClickListener {
            // Dismiss the window
            stopMediaPlayerMusic(context)
            windowManager.removeView(view)
        }

        // Add the view to the window manager
        windowManager.addView(view, layoutParams)
    }

/*
    private fun notification(context: Context, title: String, contentText: String) {
        createAlarmChannel(context)

        val soundUri: Uri? = null
        // Create a notification with dismiss and stop music actions
        val builder = NotificationCompat.Builder(context, Utils.CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(title)
            .setContentText(contentText)
            .setSound(soundUri)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOngoing(true)
            .addAction(
                R.drawable.notification_close,
                "Stop Music",
                getPendingIntentForStopNotification(context)
            )  
            .addAction(
                R.drawable.baseline_remove_circle_outline_24,
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
*/
    private fun createAlarmChannel(context: Context) {
        // Create a notification channel if not exists
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val name = "Alarm Notifications"
            val descriptionText = "Channel for alarm notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(
                Utils.CHANNEL_ID,
                name,
                importance,
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
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
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
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun dismissNotification(context: Context?) {
        context?.let {
            NotificationManagerCompat.from(it).cancel(Utils.NOTIFICATION_ID)
        }
    }

    private fun stopMediaPlayerMusic(context: Context?) {
        context?.let {
            MediaPlayerSingleton.getInstance(context).stop()
          //  Toast.makeText(context, "Media player stopped", Toast.LENGTH_SHORT).show()
        }
    }

    fun notificationLogic(context_popUp: Context?, intent_popUp: Intent?) {
        if (context_popUp != null && intent_popUp != null) {
            val action = intent_popUp.action
            if (action != null && action == Utils.STOP_NOTIFICATION) {
                stopMediaPlayerMusic(context_popUp)
                return
            } else if (action != null && action == Utils.DISMISS_NOTIFICATION) {
                MediaPlayerSingleton.stop()
                NotificationManagerCompat.from(context_popUp).cancel(Utils.NOTIFICATION_ID)
                return
            }

            val sharedPreferences = context_popUp.getSharedPreferences(
                Utils.ALERT_DATA_SP,
                Context.MODE_PRIVATE
            )
            val modelAlertJson = sharedPreferences?.getString(Utils.MODEL_ALERT_GSON, null)

            if (modelAlertJson != null) {
                val gson = Gson()
                val modelAlert = gson.fromJson(modelAlertJson, Model_Alert::class.java)

                if (modelAlert.alerts.isNotEmpty()) {
                    showNotification(
                        context_popUp,
                        "Dangerous Situation",
                        "${modelAlert.alerts[0].description}"
                    )
                } else {
                    showNotification(context_popUp, "The weather is fine", "Enjoy your day!!")
                }
            } else {
                Toast.makeText(context_popUp, "The json is null", Toast.LENGTH_SHORT).show()
                Log.i("TAG", "onReceive: AlarmReceiver Alart , The json is null ")
            }
        }
    }

    private fun showNotification(context: Context, title: String, contentText: String) {
        createNotificationChannel(context)


        val builder = NotificationCompat.Builder(context, Utils.CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(title)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(
                R.drawable.baseline_remove_circle_outline_24,
                "Dismiss",
                getPendingIntentForDismissNotification(context)
            )

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






}

object MediaPlayerSingleton {
    private var mediaPlayer: MediaPlayer? = null

    fun getInstance( contextObject: Context): MediaPlayer {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(contextObject, R.raw.notification_music)
        }
        // Additional initialization or configuration based on contextObject if needed
        return mediaPlayer!!
    }

    // Optional: Add a method to release the MediaPlayer when it's no longer needed.
    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }


}
