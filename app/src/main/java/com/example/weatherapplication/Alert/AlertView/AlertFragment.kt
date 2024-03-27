import android.app.*
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.material.rememberDismissState
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productsmvvm.Database.WeatherLocalDataSourceImplementation
import com.example.weatherapplication.Repository.WeatherRepositoryImplementation
import com.example.productsmvvm.Network.WeatherRemoteDataSourceImplementation
import com.example.weatherapplication.Alert.AlertView.AlarmReceiver
import com.example.weatherapplication.Alert.AlertView.AlertAdapter

import com.example.weatherapplication.Alert.AlertViewModel.AlertViewModel
import com.example.weatherapplication.Alert.AlertViewModel.AlertViewModelFactory_RDS
import com.example.weatherapplication.Constants.Utils
import com.example.weatherapplication.Constants.Utils.Companion.NOTIFICATION_ID
import com.example.weatherapplication.FavouriteCity.FavouriteCityView.FavouriteCityFragment
import com.example.weatherapplication.FavouriteCityWeather.FavouriteCityWeatherView.FavouriteCityWeatherActivity
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import com.example.weatherapplication.Model.FavouriteCityModel.MyApplicationFavouriteCityModel.Model_FavouriteCity
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
    private var notificationCreated = false
    var isAlarmClicked = true
     var isAlarmCliked=true

    companion object {
        private var instance: AlertFragment? = null

        fun getInstance(): AlertFragment {
            if (instance == null) {
                instance = AlertFragment()
            }
            return instance!!
        }
    }

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

            model_Time_Instance.latitude = alertResponse.lat.toString()
            model_Time_Instance.longitude = alertResponse.lon.toString()
            if(alertResponse.lat!=null && alertResponse.lon != null){
                var city = findCityName(alertResponse.lat!! , alertResponse.lon!!)
                model_Time_Instance.city = city
            }
            adapter_Instance_InAlertFragment.receiveodelTimeInAlertAdapter(model_Time_Instance)



        }

        alertViewModel_Instance_InAlertFragmet.getAlert_FromRetrofit_InAlertViewModel(Utils.LAT_ALERT,Utils.lON_ALERT, Utils.API_KEY)
    }


    private fun setAlarm(selectedDateTime: Date) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(requireContext(), AlarmReceiver::class.java)

        alarmIntent.putExtra(Utils.NOTIFICATION_KEY,"false")


        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            Utils.ALARM_REQUEST_CODE,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE // Add FLAG_IMMUTABLE
        )

        // Calculate the time difference between the current time and the selected time
        val currentTimeMillis = System.currentTimeMillis()
        val selectedTimeMillis = selectedDateTime.time
        val delayInMillis = selectedTimeMillis - currentTimeMillis

        // Ensure that the delay is positive
        if (delayInMillis > 0) {
            // Set the alarm to trigger at the specified selectedDateTime
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    selectedTimeMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    selectedTimeMillis,
                    pendingIntent
                )
            }

            // Show a toast message indicating the successful setting of the alarm
            Toast.makeText(requireContext(), "Alarm set successfully", Toast.LENGTH_SHORT).show()
        } else {
            // If the selected time is in the past, show an error message
            Toast.makeText(requireContext(), "Invalid time selected", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun showDateTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        // Start date picker
        val startDatePickerDialog = DatePickerDialog(requireContext(), { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)

            val startDate = calendar.time
            Log.i("Alert", "showDateTimePickerDialog: startDate: $startDate ")

            // End date picker
            val endDatePickerDialog = DatePickerDialog(requireContext(), { _, year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)

                val endDate = calendar.time
                Log.i("Alert", "showDateTimePickerDialog: endDate: $endDate")


                // Time picker

                showTimePickerDialog(startDate, endDate)
            }, currentYear, currentMonth, currentDay)

            endDatePickerDialog.setTitle("End Date")
            endDatePickerDialog.show()
        }, currentYear, currentMonth, currentDay)

        startDatePickerDialog.setTitle("Start Date")
        startDatePickerDialog.show()
    }
    @RequiresApi(Build.VERSION_CODES.S)
    private fun showTimePickerDialog(startDate: Date, endDate: Date) {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(requireActivity(), { _, hourOfDay, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)

            val selectedTime = calendar.time
            Log.i("Alert", "showTimePickerDialog: selectedTime: $selectedTime")

            // Process the selected start date, end date, and time
            processSelectedDateTime(startDate, endDate, selectedTime)

            // Show dialog to choose between notification and alarm
            showNotificationOrAlarmDialog(selectedTime)

        }, currentHour, currentMinute, true)

        timePickerDialog.setTitle("Specific Time")
        timePickerDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun processSelectedDateTime(startDate: Date, endDate: Date, selectedTime: Date) {
        val calendar = Calendar.getInstance()
        calendar.time = startDate
        val endCalendar = Calendar.getInstance()
        endCalendar.time = endDate

        // Loop through each day between start date and end date
        while (calendar.before(endCalendar) || calendar == endCalendar) {
            // Set the selected time on the current date
            calendar.set(Calendar.HOUR_OF_DAY, selectedTime.hours)
            calendar.set(Calendar.MINUTE, selectedTime.minutes)

            val scheduledTime = calendar.time
            Log.i("TAG", "processSelectedDateTime: scheduledTime: $scheduledTime")

            // Schedule notification or alarm for the current date and time
            scheduleNotificationOrAlarm(scheduledTime)



            // Move to the next day
            calendar.add(Calendar.DATE, 1)
        }

        model_Time_Instance.startDate=startDate.toString()
        model_Time_Instance.endDate=endDate.toString()
        model_Time_Instance.specificTime=selectedTime.toString()
        adapter_Instance_InAlertFragment.receiveodelTimeInAlertAdapter(model_Time_Instance)

    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun scheduleNotificationOrAlarm(scheduledTime: Date) {
        val currentTime = Calendar.getInstance().timeInMillis
        val delayInMillis = scheduledTime.time - currentTime

        if (delayInMillis <= 0) {
            // If the selected time has already passed, skip scheduling for this time
            Toast.makeText(
                requireContext(),
                "The time you selected has already passed",
                Toast.LENGTH_SHORT
            ).show()
            return
        }


        // Schedule notification
        val alarmManager =
            requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            currentTime + delayInMillis,
            getPendingNotificationIntent(NOTIFICATION_ID)
        )

        // Increment notification ID for each scheduled notification
        NOTIFICATION_ID++



    }

    private fun getPendingNotificationIntent(notificationId: Int): PendingIntent {
        val notificationIntent = Intent(requireContext(), AlarmReceiver::class.java)
        notificationIntent.putExtra("notification_id", notificationId)

            notificationIntent.putExtra(Utils.NOTIFICATION_KEY,"true")


        return PendingIntent.getBroadcast(
            requireContext(),
            notificationId,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }



    @RequiresApi(Build.VERSION_CODES.S)
    private fun showNotificationOrAlarmDialog(selectedDateTime: Date) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose Action")
        var selectedAction: String? = null
        builder.setPositiveButton("Set Alarm") { _, _ ->
            setAlarm(selectedDateTime)
        }
        builder.setNegativeButton("Set Notification") { _, _ ->


            requestDrawOverAppsPermission(selectedDateTime)

        } 

        builder.create().show()


    }


    fun findCityName(lat:Double , lon: Double): String{
        var cityName = ""
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val fullAddress = geocoder.getFromLocation(lat,lon,1)
        if(fullAddress != null){
            if(fullAddress.isNotEmpty()){
                val address = fullAddress.get(0)
                cityName = address.adminArea

            }
        }
        return cityName
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun requestDrawOverAppsPermission(selectedDateTime: Date) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            !Settings.canDrawOverlays(requireContext())
        ) {
            // Request "Draw over other apps" permission
            val intent =
                Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + requireContext().packageName))
            startActivityForResult(intent, Utils.REQUEST_DRAW_OVER_APPS_PERMISSION)
        } else {
            // Permission already granted, create notification directly
            scheduleNotificationOrAlarm(selectedDateTime)
            notificationCreated = true
        }
    }


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Utils.REQUEST_DRAW_OVER_APPS_PERMISSION && resultCode == Activity.RESULT_OK && !notificationCreated) {
            // Permission granted and notification not yet created, create notification
            scheduleNotificationOrAlarm(selectedDateTime!!)
            notificationCreated = true
        }else {
            Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
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







}
