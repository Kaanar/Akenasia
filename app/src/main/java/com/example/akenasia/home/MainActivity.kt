package com.example.akenasia.home

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.akenasia.databinding.ActivityMainBinding
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.location.*

import androidx.appcompat.app.AppCompatDelegate
import com.example.akenasia.R


class MainActivity : AppCompatActivity() {


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var appSettingPrefs: SharedPreferences
    lateinit var locationRequest: LocationRequest
    val PERMISSION_ID = 1

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appSettingPrefs= getSharedPreferences("AppSettingPrefs", 0)
        val isNightModeOn: Boolean = appSettingPrefs.getBoolean("NightMode", false)

        if(isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        //setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.include) as NavHostFragment?
        val navController = navHostFragment?.navController
        if (navController != null) {
            appBarConfiguration = AppBarConfiguration(navController.graph)
        }
        if (navController != null) {
            setupActionBarWithNavController(navController, appBarConfiguration)
        }

    }
}



