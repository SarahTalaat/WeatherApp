package com.example.weatherapplication

import AlertFragment
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction


import com.example.weatherapplication.Constants.Utils
import com.example.weatherapplication.CurrentWeather.CurrentWeatherView.CurrentWeatherFragment
import com.example.weatherapplication.FavouriteCity.FavouriteCityView.FavouriteCityFragment
import com.example.weatherapplication.Settings.SettingsFragment
import com.google.android.material.navigation.NavigationView
import java.util.Locale

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lang = getLanguage()
        setLocale(lang)
        setContentView(R.layout.activity_main)



        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        /*
        Log.i("TAG", "onCreate: Main Activity (Before if) : SavedInstanceState value =  " +savedInstanceState)
        if (savedInstanceState==null){
            Log.i("TAG", "onCreate: Main Activity: (inside if) SavedInstanceState value =  " +savedInstanceState)

            replaceFragment(CurrentWeatherFragment())
            navigationView.setCheckedItem(R.id.nav_home)
        }else if(bundleMessage == Utils.FAVOURITE_CITY_VALUE){
            replaceFragment(FavouriteCityFragment())
        }

         */

        // Check if there is data in the intent extras
        val bundleMessage = intent.getStringExtra(Utils.FAVOURITE_CITY_KEY)
        val bundleMessage2 = intent.getStringExtra(Utils.SETTINGS_FRAGMENT_KEY)
        Log.i("TAG", "onCreate: Main Activity: Favourite city value from key: " + bundleMessage)
        if (bundleMessage == Utils.FAVOURITE_CITY_VALUE) {
            replaceFragment(FavouriteCityFragment())
            navigationView.setCheckedItem(R.id.nav_favourites)

        } else if(bundleMessage2 == Utils.SETTINGS_FRAGMENT_VALUE){
            replaceFragment(SettingsFragment())
            navigationView.setCheckedItem(R.id.nav_settings)

        } else {
            replaceFragment(CurrentWeatherFragment())
            navigationView.setCheckedItem(R.id.nav_home)
        }




    }



    private fun replaceFragment(fragment: Fragment){
        currentFragment = fragment
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //there is no super in the vdeo
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home -> replaceFragment(CurrentWeatherFragment())
            R.id.nav_favourites -> replaceFragment(FavouriteCityFragment())
            R.id.nav_alerts -> replaceFragment(AlertFragment())
            R.id.nav_settings -> replaceFragment(SettingsFragment())
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    private fun getLanguage(): String {
        val sharedPreferences = this.getSharedPreferences(Utils.SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        var spLangValue = sharedPreferences?.getString(Utils.LANGUAGE_KEY, null)

        return spLangValue ?: Utils.ENGLISH // Default to English if not found
    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val resources: Resources = resources
        val configuration: Configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }






}