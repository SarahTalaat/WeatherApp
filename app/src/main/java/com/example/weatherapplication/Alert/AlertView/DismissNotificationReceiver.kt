/*
package com.example.weatherapplication.Alert.AlertView

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import com.example.weatherapplication.R

class DismissNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "DISMISS_NOTIFICATION") {
            // Stop the notification sound here
            // You can implement the logic to stop the notification sound from the media player
            // For example, if you're using a MediaPlayer instance, you can call mediaPlayer.stop()
            val mediaPlayer = MediaPlayer.create(context, R.raw.notification_music)
            mediaPlayer.stop()
        }
    }
}

*/