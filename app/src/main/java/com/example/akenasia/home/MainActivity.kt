package com.example.akenasia.home

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.akenasia.databinding.ActivityMainBinding
import androidx.navigation.fragment.NavHostFragment

import androidx.appcompat.app.AppCompatDelegate
import com.example.akenasia.handler.AchievementHandler
import com.example.akenasia.handler.MarqueurHandler
import com.example.akenasia.handler.PersonnageHandler
import com.example.akenasia.handler.StatsHandler
import com.example.akenasia.R
import com.example.akenasia.database.Position
import com.google.android.gms.maps.model.LatLng


class MainActivity : AppCompatActivity() {


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var appSettingPrefs: SharedPreferences
    lateinit var marqueurHandler: MarqueurHandler
    lateinit var achievementHandler: AchievementHandler
    lateinit var personnageHandler: PersonnageHandler
    lateinit var statsHandler: StatsHandler
    private lateinit var pos: Position
    private lateinit var markers: HashMap<Int, LatLng>

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        marqueurHandler= MarqueurHandler(this)
        achievementHandler= AchievementHandler(this)
        personnageHandler= PersonnageHandler(this)
        statsHandler= StatsHandler(this)
        markers= HashMap()
        pos = Position(this)
        pos.refreshLocation()


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

    override fun onStart() {
        super.onStart()
        //Initialisation des données du jeu
        initialisation()
    }

    private fun initialisation(){

        if (personnageHandler.view().isEmpty()) {
            personnageHandler.create()
        }
        if (statsHandler.getStatsCount().toInt() == 0) {
            statsHandler.create(1)
        }
    }

}



