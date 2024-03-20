package com.example.weatherapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        if (bundleMessage == Utils.FAVOURITE_CITY_VALUE) {
            replaceFragment(FavouriteCityFragment())
            navigationView.setCheckedItem(R.id.nav_favourites)
        } else {
            replaceFragment(CurrentWeatherFragment())
            navigationView.setCheckedItem(R.id.nav_home)
        }

    }



    private fun replaceFragment(fragment: Fragment){
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
            R.id.nav_alerts -> replaceFragment(AlertsFragment())
            R.id.nav_settings -> replaceFragment(SettingsFragment())
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}