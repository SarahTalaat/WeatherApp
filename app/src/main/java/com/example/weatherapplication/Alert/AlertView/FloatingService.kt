package com.example.weatherapplication.Alert.AlertView

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.WindowManager
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService

// Kotlin code
class FloatingService : Service() {
    override fun onCreate() {
        super.onCreate()
        // Create and attach your floating view here
        // Example: Add a TextView as a floating widget
        val textView = TextView(this).apply {
            text = "Floating Widget"
            setBackgroundColor(Color.WHITE)
            setTextColor(Color.BLACK)
        }
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.addView(textView, params)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
