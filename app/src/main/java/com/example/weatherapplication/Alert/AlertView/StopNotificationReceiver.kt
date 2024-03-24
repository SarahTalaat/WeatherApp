package com.example.weatherapplication.Alert.AlertView

import AlertFragment
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.widget.Toast
import com.example.weatherapplication.MainActivity
import com.example.weatherapplication.R

    
class StopNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {

            AlertFragment.getInstance().stopMediaPlayer()

            // Dismiss the notification
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationId = intent?.getIntExtra(Constants.NOTIFICATION_ID_EXTRA, 0) ?: 0
            notificationManager.cancel(notificationId)
        }
    }

}
