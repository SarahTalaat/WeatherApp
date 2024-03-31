import android.app.Activity
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productsmvvm.Database.WeatherLocalDataSourceImplementation
import com.example.productsmvvm.FavouriteProducts.FavouriteProductsView.OnAlertClickListenerInterface
import com.example.productsmvvm.Network.WeatherRemoteDataSourceImplementation
import com.example.weatherapplication.Alert.AlertView.AlarmReceiver
import com.example.weatherapplication.Alert.AlertView.AlertAdapter
import com.example.weatherapplication.Alert.AlertViewModel.AlertViewModel
import com.example.weatherapplication.Alert.AlertViewModel.AlertViewModelFactory_RDS
import com.example.weatherapplication.Constants.Utils
import com.example.weatherapplication.Constants.Utils.Companion.NOTIFICATION_ID
import com.example.weatherapplication.Map.MapView.MapActivity
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import com.example.weatherapplication.Network.ApiState
import com.example.weatherapplication.R
import com.example.weatherapplication.Repository.WeatherRepositoryImplementation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import java.util.Locale


const val REQUEST_LOCATION_CODE = 2005

class AlertFragment : Fragment() , OnAlertClickListenerInterface {

    private lateinit var fab_addAlert_InAlertFragment: FloatingActionButton
    private var selectedDateTime: Date? = null
    private lateinit var alertViewModelFactory_Instance_RDS_InAlertFragment: AlertViewModelFactory_RDS
    private lateinit var alertViewModel_Instance_InAlertFragmet: AlertViewModel
    private lateinit var recyclerView_Instance_InAlertFragment: RecyclerView
    private lateinit var layoutManager_Instance_InAlertFragment: LinearLayoutManager
    private lateinit var adapter_Instance_InAlertFragment: AlertAdapter
    var model_Time_Instance : Model_Time = Model_Time()
    var isAlertsNotEmpty: Boolean = false
    private var notificationCreated = false
    var isClicked = true
    var isAppear = false
    var index = 0
    var shallCardAppear = false
    private val REQUEST_CODE_MAP_ACTIVITY = 123
    lateinit var progressBar: ProgressBar




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
            val intent = Intent(activity, MapActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_MAP_ACTIVITY)
          //  showDateTimePickerDialog()
        }

        progressBar=view.findViewById(R.id.progressBar_alert)

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

        alertViewModel_Instance_InAlertFragmet.getAllLocalModelTime_StoredInDatabase_InAlertViewModel()

        saveInSharedPreferencesToAlarmReceiver()

        lifecycleScope.launch {
            alertViewModel_Instance_InAlertFragmet.alertStateFlowList_ModelTime_InAlertViewModel.collectLatest { result ->
                when(result) {
                    is ApiState.Loading ->{
                        progressBar.visibility = View.VISIBLE
                        recyclerView_Instance_InAlertFragment.visibility = View.GONE
                    }
                    is ApiState.Failure ->{
                        progressBar.visibility = View.GONE
                        Toast.makeText(context,"There is problem in the server or fetching data from database!!!!!!!!!!!!", Toast.LENGTH_LONG).show()
                    }
                    is ApiState.Success_ModelForecast_Remote_InApiState -> {
                        Log.i("TAG", "onViewCreated: AlertFragment APIStateResult sucess modelforecast ")

                    }
                    is ApiState.Success_ModelAlert_Remote_InApiState -> {
                        Log.i("TAG", "onViewCreated: AlertFragment ApiState.Success_ModelAlert_Remote_InApiState ")
                    }
                    is ApiState.Success_ModelTime_Local_InApiState -> {
                        progressBar.visibility = View.GONE
                        recyclerView_Instance_InAlertFragment.visibility = View.VISIBLE

                        if (result.data !=null){
                            adapter_Instance_InAlertFragment.setModelTimeArrayList_StoredInDatabase_InAlertAdapter( result.data as ArrayList<Model_Time>)
                            adapter_Instance_InAlertFragment.notifyDataSetChanged()
                        }
                    }
                    is ApiState.Success_ModelFavouriteCity_Local_InApiState -> {
                        Log.i("TAG", "onViewCreated: AlertFragment APIStateResult sucess model favourite city ")

                    }
                }
            }
        }


        lifecycleScope.launch {
            alertViewModel_Instance_InAlertFragmet.alertStateFlow_InAlertViewModel.collectLatest { result ->
                when(result){
                    is ApiState.Loading -> {
                        Log.i("TAG", "onViewCreated: Alert Fragment ApiState.Loading 2")

                    }
                    is ApiState.Success_ModelForecast_Remote_InApiState -> {
                        Log.i("TAG", "onViewCreated: AlertFragment APIStateResult sucess modelforecast 2")
                    }
                    is ApiState.Success_ModelTime_Local_InApiState -> {
                        Log.i("TAG", "onViewCreated: Alert Fragment : ApiState.Success_ModelTime_Local_InApiState 2")

                    }
                    is ApiState.Success_ModelFavouriteCity_Local_InApiState -> {
                        Log.i("TAG", "onViewCreated: AlertFragment APIStateResult sucess model favourite city 2")

                    }
                    is ApiState.Failure -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(context,"There is problem in the server or fetching data from database!!!!!!!!!!!!", Toast.LENGTH_LONG).show()
                    }
                    is ApiState.Success_ModelAlert_Remote_InApiState ->{
                        recyclerView_Instance_InAlertFragment.visibility = View.VISIBLE

                        if (result.data?.alerts?.isNotEmpty() == true) {
                            isAlertsNotEmpty = true
                        } else {
                            isAlertsNotEmpty = false
                        }
                        model_Time_Instance.latitude = result.data?.lat.toString()
                        Log.i("NULL", "onViewCreated: lat : ${index+1} ")
                        model_Time_Instance.longitude = result.data?.lon.toString()
                        Log.i("NULL", "onViewCreated: lon : ${index+1} ")

                        if(result.data?.lat!=null && result.data.lon != null){
                            var city = findCityName(result.data.lat!! , result.data.lon!!)
                            model_Time_Instance.city = city
                            var lat = result.data.lat
                            var lon = result.data.lon

                            Log.i("NULL", "onViewCreated: city: ${index+1}")
                            //  alertViewModel_Instance_InAlertFragmet.insertModelTime_InAlertViewModel(model_Time_Instance)
                        }
                        //   adapter_Instance_InAlertFragment.receiveModelTimeInAlertAdapter(model_Time_Instance)
                        //   adapter_Instance_InAlertFragment.notifyDataSetChanged()
                    }

                }

            }

        }


    }


    private fun setAlarmWithCoroutine(selectedDateTime: Date) {
        CoroutineScope(Dispatchers.Main).launch {
            val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmIntent = Intent(requireContext(), AlarmReceiver::class.java)
            if (isClicked) {
                isAppear = true
                model_Time_Instance.shallCardAppear = true
                alarmIntent.putExtra(Utils.NOTIFICATION_ALERT_KEY, "false")
            }

            val pendingIntent = PendingIntent.getBroadcast(
                requireContext(),
                Utils.ALARM_REQUEST_CODE,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val currentTimeMillis = System.currentTimeMillis()
            val selectedTimeMillis = selectedDateTime.time
            val delayInMillis = selectedTimeMillis - currentTimeMillis

            if (delayInMillis > 0) {
                // Set the alarm using a coroutine
                Handler(Looper.getMainLooper()).postDelayed({
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
                    Toast.makeText(requireContext(), "Alarm set successfully", Toast.LENGTH_SHORT).show()
                }, delayInMillis)
            } else {
                Toast.makeText(requireContext(), "Invalid time selected", Toast.LENGTH_SHORT).show()
            }
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
            var context: Context = requireContext()
            showNotificationOrAlarmDialog(context,selectedTime)
            model_Time_Instance.shallCardAppear=true

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
            model_Time_Instance.specificTime=scheduledTime.toString()
            //  alertViewModel_Instance_InAlertFragmet.insertModelTime_InAlertViewModel(model_Time_Instance)
            Log.i("NULL", "processSelectedDateTime: specificTime${index+1}")

            // Schedule notification or alarm for the current date and time
            setNotification(scheduledTime)



            // Move to the next day
            calendar.add(Calendar.DATE, 1)
        }

        model_Time_Instance.startDate=startDate.toString()
        Log.i("NULL", "processSelectedDateTime: startDate ${index+1}")
        model_Time_Instance.endDate=endDate.toString()
        Log.i("NULL", "processSelectedDateTime: endDate ${index+1}")

    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun setNotification(scheduledTime: Date) {
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
       // notificationIntent.putExtra("notification_id", notificationId)

        if(isClicked==false){
            isAppear=true
            model_Time_Instance.shallCardAppear=true
            notificationIntent.putExtra(Utils.NOTIFICATION_ALERT_KEY,"true")
        }


        return PendingIntent.getBroadcast(
            requireContext(),
            notificationId,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    fun saveInSharedPreferencesToAlarmReceiver(){
        Log.i("TAG", "onViewCreated: AlertFragment : alertResponse:  $model_Time_Instance")
        val gson = Gson()
        val modelAlertJson = gson.toJson(model_Time_Instance)
        val sharedPreferences = requireContext().getSharedPreferences(Utils.ALERT_DATA_SP, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(Utils.MODEL_ALERT_GSON, modelAlertJson)
        editor.apply()
    }

/*
    @RequiresApi(Build.VERSION_CODES.S)
    private fun showNotificationOrAlarmDialog(selectedDateTime: Date) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose Action")
        builder.setPositiveButton("Set Alarm") { _, _ ->
            if(isCicked==true){
                requestDrawOverAppsPermission(selectedDateTime)
                isCicked=false
            }
        }
        // Negative button click listener
        builder.setNegativeButton("Set Notification") { _, _ ->
            if(isCicked==true){
                requestDrawOverAppsPermission(selectedDateTime)
                isCicked=false
            }
        }

        var dialog = builder.create()
        dialog.show()

    }

  */

    @RequiresApi(Build.VERSION_CODES.S)
    private fun showNotificationOrAlarmDialog(context: Context, selectedDateTime: Date) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.custom_dialog_layout)


        val notificationButton = dialog.findViewById<Button>(R.id.notificationButton)
        notificationButton.setOnClickListener {
            // Handle setting a notification here
            isClicked=false
            requestDrawOverAppsPermission(selectedDateTime)
            dialog.dismiss()
            model_Time_Instance.shallCardAppear=true
            alertViewModel_Instance_InAlertFragmet.insertModelTime_InAlertViewModel(model_Time_Instance)
            adapter_Instance_InAlertFragment.notifyDataSetChanged()
        }

        val alarmButton = dialog.findViewById<Button>(R.id.alarmButton)
        alarmButton.setOnClickListener {
            // Handle setting an alarm here
            isClicked=true
            requestDrawOverAppsPermission(selectedDateTime)
            dialog.dismiss()
            model_Time_Instance.shallCardAppear=true
            alertViewModel_Instance_InAlertFragmet.insertModelTime_InAlertViewModel(model_Time_Instance)
            adapter_Instance_InAlertFragment.notifyDataSetChanged()
        }


        dialog.show()
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
            if (isClicked) {
                setAlarmWithCoroutine(selectedDateTime!!)
            } else {
                setNotification(selectedDateTime!!)
            }


        }
    }


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Utils.REQUEST_DRAW_OVER_APPS_PERMISSION && resultCode == Activity.RESULT_OK && !notificationCreated) {
            // Permission granted and notification not yet created, create notification
            if (isClicked) {
                setAlarmWithCoroutine(selectedDateTime!!)
            } else {
                setNotification(selectedDateTime!!)
            }
            notificationCreated = true
        }else if(requestCode == REQUEST_CODE_MAP_ACTIVITY){
            if (resultCode == Activity.RESULT_OK) {
                if(getLatitudeFromSharedPreferences(requireContext()) != null && getLongitudeFromSharedPreferences(requireContext()) != null){
                    var choosenLat = getLatitudeFromSharedPreferences(requireContext())!!
                    Log.i("TAG", "onViewCreated: Alert Fragment : choosenLat: $choosenLat")

                    var choosenLon = getLongitudeFromSharedPreferences(requireContext())!!
                    Log.i("TAG", "onViewCreated: Alert Fragment : choosenLon: $choosenLon")

                    alertViewModel_Instance_InAlertFragmet.getAlert_FromRetrofit_InAlertViewModel(choosenLat,choosenLon, Utils.API_KEY)
                    adapter_Instance_InAlertFragment.setModelTimeArrayList_FromRetrofit_InAlertAdapter(model_Time_Instance)
                    adapter_Instance_InAlertFragment.notifyDataSetChanged()
                }
                showDateTimePickerDialog()
                // Continue your code from where you left
            } else {
                // Handle if the activity was not finished successfully
            }
        }else{
            Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()

        }
    }


    private fun initUI_InAlertFragment(view: View){
        recyclerView_Instance_InAlertFragment = view.findViewById(R.id.rv_alert)
    }

    private fun setUpRecyclerView_InAlertFragment(){
        layoutManager_Instance_InAlertFragment = LinearLayoutManager(requireContext())
        layoutManager_Instance_InAlertFragment.orientation = RecyclerView.VERTICAL
        adapter_Instance_InAlertFragment = AlertAdapter(requireContext(), ArrayList(),this)
        recyclerView_Instance_InAlertFragment.adapter = adapter_Instance_InAlertFragment
        recyclerView_Instance_InAlertFragment.layoutManager = layoutManager_Instance_InAlertFragment
    }

    override fun onClick_DeleteModelTimeFromAlertFragment_InOnAlertClickListenerInterface(modelTime: Model_Time) {
        alertViewModel_Instance_InAlertFragmet.deleteModelTime_InAlertViewModel(modelTime)
    }

    override fun onClick_InserModelTimeToAlertFragment_InOnAlertClickListenerInterface(modelTime: Model_Time) {
        alertViewModel_Instance_InAlertFragmet.insertModelTime_InAlertViewModel(modelTime)
    }

    fun getLatitudeFromSharedPreferences(context: Context): String? {
        // Get SharedPreferences instance
        val sharedPreferences = context.getSharedPreferences(Utils.ALERT_MAP_SP_KEY, Context.MODE_PRIVATE)
        // Retrieve latitude from SharedPreferences
        return sharedPreferences.getString(Utils.ALERT_MAP_SP_LAT, "")
    }

    // Function to retrieve longitude from SharedPreferences
    fun getLongitudeFromSharedPreferences(context: Context): String? {
        // Get SharedPreferences instance
        val sharedPreferences = context.getSharedPreferences(Utils.ALERT_MAP_SP_KEY, Context.MODE_PRIVATE)
        // Retrieve longitude from SharedPreferences
        return sharedPreferences.getString(Utils.ALERT_MAP_SP_LON, "")
    }


}
