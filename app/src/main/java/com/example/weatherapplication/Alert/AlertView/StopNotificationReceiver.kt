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
            val fragment = (context as MainActivity).currentFragment as? AlertFragment
            // Call the stopMediaPlayer method if fragment is not null
            fragment?.stopMediaPlayer()
            // Dismiss the notification
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationId = intent?.getIntExtra(Constants.NOTIFICATION_ID_EXTRA, 0) ?: 0
            notificationManager.cancel(notificationId)
        }
    }

}
