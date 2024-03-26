package com.example.weatherapplication.Alert.AlertView

import AlertFragment
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log


import android.widget.Button

import android.widget.RemoteViews


import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.weatherapplication.Constants.Utils
import com.example.weatherapplication.Model.AlertModel.APIModel.Model_Alert
import com.example.weatherapplication.R
import com.google.gson.Gson


import org.w3c.dom.Text


class AlarmReceiver : BroadcastReceiver() {

    var isNotification = true

import android.view.Gravity
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import com.example.weatherapplication.MainActivity


class AlarmReceiver : BroadcastReceiver() {



    override fun onReceive(context: Context?, intent: Intent?) {

        Log.i("TAG", "onReceive: AlarmReceiver: context = $context ")
        Log.i("TAG", "onReceive: AlarmReceiver: intent.action = ${intent?.action} ")

        val data = intent?.getStringExtra(Utils.ISNOTIFICATION)
        val timeFromIntent = intent?.getStringExtra(Utils.SPECIFICTIME)

        Log.i("TAG", "onReceive: data = $data")
        Log.i("TAG", "onReceive: timeFromIntent = $timeFromIntent")

        if (data == "true") {
            showNotification(context, intent)
        } else if (data == "false") {

            if (timeFromIntent != null) {
                var dateArray = timeFromIntent.split(" ").get(3).split(":")
                var hour = dateArray.get(0).toInt()
                var minutes = dateArray.get(1).toInt()
                var seconds = dateArray.get(2).toInt()

                Log.i("TAG", "onReceive: hour = $hour , minutes = $minutes , seconds= $seconds ")

                var triggerTime = calculateTriggerAtMillis(hour, minutes, seconds)

            }

        }


    }

    fun calculateTriggerAtMillis(hours: Int, minutes: Int, seconds: Int): Long {
        val currentTimeMillis = System.currentTimeMillis()
        return currentTimeMillis + (hours * 60 * 60 * 1000) + (minutes * 60 * 1000) + (seconds * 1000)
    }


    private fun stopMediaPlayer(context: Context) {
        // Stop the media player here
        MediaPlayerSingleton.getInstance(context).stop()
        Toast.makeText(context, "Media player stopped", Toast.LENGTH_SHORT).show()
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

        val action = intent?.action
        if (action != null) {
            when (action) {
                Utils.STOP_NOTIFICATION -> {
                    stopMediaPlayerMusic(context)
                }
                Utils.DISMISS_NOTIFICATION -> {
                    stopMediaPlayerMusic(context)
                    dismissNotification(context)

    fun showNotification(context: Context?, intent: Intent?) {

        if (context != null && intent != null) {
            MediaPlayerSingleton.getInstance(context).start()
            val action = intent.action
            if (action != null && action == Utils.STOP_NOTIFICATION) {
                stopMediaPlayer(context)
                return
            } else if (action != null && action == Utils.DISMISS_NOTIFICATION) {
                context?.let {
                    MediaPlayerSingleton.stop()
                    NotificationManagerCompat.from(it).cancel(Utils.NOTIFICATION_ID)

                }
            }
        }

        val retrievedValue = intent?.getStringExtra(Utils.NOTIFICATION_KEY)

        if(retrievedValue== "true"){
            isNotification = true
            notificationLogic(context, intent)
        }else if(retrievedValue == "false"){
            isNotification = false
            popUpNotificationLogic(context, intent)
        }else{
            Log.i("TAG", "onReceive: No true or false value on the intent")
        }


    }

    fun notificationLogic(context:Context?, intent: Intent?){

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
    private fun notification(context: Context, title: String, contentText: String) {
        createNotificationChannel(context)

        val soundUri: Uri? = null
        // Create a notification with dismiss and stop music actions
        val builder = NotificationCompat.Builder(context, Utils.CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(title)
            .setContentText(contentText)
            .setSound(soundUri)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
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


    private fun createNotificationChannel(context: Context) {
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
            Toast.makeText(context, "Media player stopped", Toast.LENGTH_SHORT).show()
        }
    }

    fun popUpNotificationLogic(context_popUp: Context?, intent_popUp: Intent?) {
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
                    popUpNotification(
                        context_popUp,
                        "Dangerous Situation",
                        "${modelAlert.alerts[0].description}"
                    )
                } else {
                    popUpNotification(context_popUp, "The weather is fine", "Enjoy your day!!")
                }
            } else {
                Toast.makeText(context_popUp, "The json is null", Toast.LENGTH_SHORT).show()
                Log.i("TAG", "onReceive: AlarmReceiver Alart , The json is null ")
            }
        }
    }

    private fun popUpNotification(context: Context, title: String, contentText: String) {
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


        fun getInstance(contextObject: Context): MediaPlayer {
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



}

}
