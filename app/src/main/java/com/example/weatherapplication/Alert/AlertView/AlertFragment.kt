package com.example.weatherapplication.Alert.AlertView

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import com.example.weatherapplication.Constants.Utils
import com.example.weatherapplication.R
import com.example.weatherapplication.MainActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

const val REQUEST_LOCATION_CODE = 2005

class AlertFragment : Fragment() {

    private lateinit var fab_addAlert_InAlertFragment: FloatingActionButton
    private var selectedDateTime: Date? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_alert, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab_addAlert_InAlertFragment = view.findViewById(R.id.floatingActionButton_addAlert)
        fab_addAlert_InAlertFragment.setOnClickListener {
            showDateTimePickerDialog()
        }
    }

    private fun showDateTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)

            showTimePickerDialog(calendar)
        }, currentYear, currentMonth, currentDay)

        datePickerDialog.show()
    }

    private fun showTimePickerDialog(calendar: Calendar) {
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)

            selectedDateTime = calendar.time
            showNotificationOrAlarmDialog(selectedDateTime!!)
        }, currentHour, currentMinute, true)

        timePickerDialog.show()
    }

    private fun showNotificationOrAlarmDialog(selectedDateTime: Date) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose Action")
        builder.setPositiveButton("Set Alarm") { _, _ ->
            setAlarm(selectedDateTime)
        }
        builder.setNegativeButton("Set Notification") { _, _ ->
            requestDrawOverAppsPermission(selectedDateTime)
        }
        builder.create().show()
    }

    private fun requestDrawOverAppsPermission(selectedDateTime: Date) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            !Settings.canDrawOverlays(requireContext())
        ) {
            val intent =
                Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + requireContext().packageName))
            startActivityForResult(intent, Constants.REQUEST_DRAW_OVER_APPS_PERMISSION)
        } else {
            createNotification(selectedDateTime)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST_DRAW_OVER_APPS_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                Settings.canDrawOverlays(requireContext())
            ) {
                createNotification(selectedDateTime!!)
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setAlarm(selectedDateTime: Date) {
        val alarmManager =
            requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            Constants.ALARM_REQUEST_CODE,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, selectedDateTime.time, pendingIntent)
        Toast.makeText(requireContext(), "Alarm set successfully", Toast.LENGTH_SHORT).show()
    }

    private fun createNotification(selectedDateTime: Date) {
        val intent = Intent(requireContext(), MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(requireContext(), Constants.CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Notification Title")
            .setContentText("Notification Description")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val futureInMillis = selectedDateTime.time - System.currentTimeMillis()
        builder.setWhen(System.currentTimeMillis() + futureInMillis)

        val alarmSound = Settings.System.DEFAULT_NOTIFICATION_URI
        builder.setSound(alarmSound)

        val notificationManager = NotificationManagerCompat.from(requireContext())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
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
        notificationManager.notify(Constants.NOTIFICATION_ID, builder.build())
    }

    companion object {
        fun newInstance() = AlertFragment()
    }

}

object Constants {
    const val ALARM_REQUEST_CODE = 1001
    const val REQUEST_DRAW_OVER_APPS_PERMISSION = 1002
    const val CHANNEL_ID = "my_channel_01"
    const val NOTIFICATION_ID = 2001
}
