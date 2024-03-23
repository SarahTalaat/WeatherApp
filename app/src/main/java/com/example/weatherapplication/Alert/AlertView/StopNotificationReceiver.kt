package com.example.weatherapplication.Alert.AlertView

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.widget.Toast
import com.example.weatherapplication.R


class StopNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            val mediaPlayer = MediaPlayer.create(context, R.raw.notification_music)
            mediaPlayer.stop()
            Toast.makeText(context, "Notification sound stopped", Toast.LENGTH_SHORT).show()

            // Dismiss the notification
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationId = intent?.getIntExtra(Constants.NOTIFICATION_ID_EXTRA, 0) ?: 0
            notificationManager.cancel(notificationId)
        }
    }
}
object Constants {
    const val ALARM_REQUEST_CODE = 1001
    const val REQUEST_DRAW_OVER_APPS_PERMISSION = 1002
    const val CHANNEL_ID = "my_channel_id"
    const val NOTIFICATION_PERMISSION_REQUEST_CODE = 2002
    const val NOTIFICATION_ID_EXTRA = "notification_id_extra"
}