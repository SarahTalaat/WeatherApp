import Constants.NOTIFICATION_ID
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myweatherapp.AlarmReceiver
import com.example.productsmvvm.Database.WeatherLocalDataSourceImplementation
import com.example.productsmvvm.Model.WeatherRepositoryImplementation
import com.example.productsmvvm.Network.WeatherRemoteDataSourceImplementation
import com.example.weatherapplication.Constants.Utils
import com.example.weatherapplication.Alert.AlertView.AlertAdapter
import com.example.weatherapplication.Alert.AlertView.DismissNotificationReceiver
import com.example.weatherapplication.Alert.AlertView.StopNotificationReceiver
import com.example.weatherapplication.Alert.AlertViewModel.AlertViewModel
import com.example.weatherapplication.Alert.AlertViewModel.AlertViewModelFactory_RDS
import com.example.weatherapplication.MainActivity
import com.example.weatherapplication.Model.Model_Time
import com.example.weatherapplication.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson

import java.util.*
import kotlin.collections.ArrayList

const val REQUEST_LOCATION_CODE = 2005

class AlertFragment : Fragment() {

    private lateinit var fab_addAlert_InAlertFragment: FloatingActionButton
    private var selectedDateTime: Date? = null
    private lateinit var alertViewModelFactory_Instance_RDS_InAlertFragment: AlertViewModelFactory_RDS
    private lateinit var alertViewModel_Instance_InAlertFragmet: AlertViewModel
    private lateinit var recyclerView_Instance_InAlertFragment: RecyclerView
    private lateinit var layoutManager_Instance_InAlertFragment: LinearLayoutManager
    private lateinit var adapter_Instance_InAlertFragment: AlertAdapter
    lateinit var model_Time_Instance : Model_Time
    var isAlertsNotEmpty: Boolean = false

    var arrayOfModelTime: ArrayList<Model_Time> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_alert, container, false)
        return view
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab_addAlert_InAlertFragment = view.findViewById(R.id.floatingActionButton_addAlert)
        fab_addAlert_InAlertFragment.setOnClickListener {
            showDateTimePickerDialog()
        }

        model_Time_Instance= Model_Time()
        alertViewModelFactory_Instance_RDS_InAlertFragment = AlertViewModelFactory_RDS(
            WeatherRepositoryImplementation.getWeatherRepositoryImplementationInstance(
                WeatherRemoteDataSourceImplementation.getWeatherRemoteDataSourceImplementation_Instance() ,
                WeatherLocalDataSourceImplementation(requireContext())
            )
        )

        alertViewModel_Instance_InAlertFragmet = ViewModelProvider(this,alertViewModelFactory_Instance_RDS_InAlertFragment).get(
            AlertViewModel::class.java)

        initUI_InAlertFragment(view)
        setUpRecyclerView_InAlertFragment()

        alertViewModel_Instance_InAlertFragmet.alertLiveDataList_InAlertViewModel.observe(viewLifecycleOwner) { alertResponse ->
            Log.i("TAG", "onViewCreated: AlertFragment : alertResponse:  $alertResponse")
            val gson = Gson()
            val modelAlertJson = gson.toJson(alertResponse)
            val sharedPreferences = requireContext().getSharedPreferences(Utils.ALERT_DATA_SP, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(Utils.MODEL_ALERT_GSON, modelAlertJson)
            editor.apply()

            if (alertResponse.alerts.isNotEmpty()) {
                isAlertsNotEmpty = true
            } else {
                isAlertsNotEmpty = false
            }
        }

        alertViewModel_Instance_InAlertFragmet.getAlert_FromRetrofit_InAlertViewModel(Utils.LAT_ALERT,Utils.lON_ALERT, Utils.API_KEY)
    }

    @RequiresApi(Build.VERSION_CODES.S)
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

    @RequiresApi(Build.VERSION_CODES.S)
    private fun showTimePickerDialog(calendar: Calendar) {
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(requireActivity(), { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)

            selectedDateTime = calendar.time
            showNotificationOrAlarmDialog(selectedDateTime!!)
        }, currentHour, currentMinute, true)

        timePickerDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.S)
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

    @RequiresApi(Build.VERSION_CODES.S)
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

    @RequiresApi(Build.VERSION_CODES.S)
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
        val currentTime = Calendar.getInstance().timeInMillis
        val delayInMillis = selectedDateTime.time - currentTime

        if (delayInMillis <= 0) {
            Toast.makeText(
                requireContext(),
                "Selected time has already passed",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        NOTIFICATION_ID++

        val intent = Intent(requireContext(), MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            requireContext(),
            Constants.NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            createNotificationChannel()
        }

        val builder = NotificationCompat.Builder(requireContext(), Constants.CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Notification Title")
            .setContentText("Notification Description")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        // Add a dismiss button to the notification
        val dismissIntent = Intent(requireContext(), DismissNotificationReceiver::class.java)
        dismissIntent.action = "DISMISS_NOTIFICATION"
        val dismissPendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            Constants.NOTIFICATION_ID,
            dismissIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val dismissAction = NotificationCompat.Action.Builder(
            R.drawable.notification_close,
            "Dismiss",
            dismissPendingIntent
        ).build()
        builder.addAction(dismissAction)

        val notificationIntent = Intent(requireContext(), AlarmReceiver::class.java)
        notificationIntent.putExtra("notification_id", Constants.NOTIFICATION_ID)

        val pendingNotificationIntent = PendingIntent.getBroadcast(
            requireContext(),
            Constants.NOTIFICATION_ID,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager =
            requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            currentTime + delayInMillis,
            pendingNotificationIntent
        )

        // Schedule playing the notification sound at the specified time
        Timer().schedule(object : TimerTask() {
            override fun run() {
                playNotificationSound()
            }
        }, delayInMillis)

        adapter_Instance_InAlertFragment.addNotification(selectedDateTime)
        adapter_Instance_InAlertFragment.notifyDataSetChanged()

        Toast.makeText(
            requireContext(),
            "Notification will be sent at the specified time",
            Toast.LENGTH_SHORT
        ).show()

        // Show the notification
        val notificationManager =
            requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }


    private fun playNotificationSound() {
        val mediaPlayer = MediaPlayer.create(requireContext(), R.raw.notification_music)
        mediaPlayer.start()
        // You can optionally handle stopping the sound after a duration if needed
    }


    @RequiresApi(Build.VERSION_CODES.S)
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My Channel"
            val descriptionText = "Channel Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(Constants.CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun initUI_InAlertFragment(view: View){
        recyclerView_Instance_InAlertFragment = view.findViewById(R.id.rv_alert)
    }

    private fun setUpRecyclerView_InAlertFragment(){
        layoutManager_Instance_InAlertFragment = LinearLayoutManager(requireContext())
        layoutManager_Instance_InAlertFragment.orientation = RecyclerView.VERTICAL
        adapter_Instance_InAlertFragment = AlertAdapter(requireContext(), ArrayList())
        recyclerView_Instance_InAlertFragment.adapter = adapter_Instance_InAlertFragment
        recyclerView_Instance_InAlertFragment.layoutManager = layoutManager_Instance_InAlertFragment
    }

    companion object {
        fun newInstance() = AlertFragment()
    }
}

object Constants {
    const val ALARM_REQUEST_CODE = 1001
    const val REQUEST_DRAW_OVER_APPS_PERMISSION = 1002
    const val CHANNEL_ID = "my_channel_id"
    var NOTIFICATION_ID = 2001 // Unique identifier for notifications
    const val NOTIFICATION_PERMISSION_REQUEST_CODE = 2002

    const val STOP_NOTIFICATION_REQUEST_CODE = 2003
    const val NOTIFICATION_ID_EXTRA = "notification_id_extra"

}
