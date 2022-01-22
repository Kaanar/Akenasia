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
import com.example.akenasia.Handler.AchievementHandler
import com.example.akenasia.Handler.MarqueurHandler
import com.example.akenasia.Handler.PersonnageHandler
import com.example.akenasia.Handler.StatsHandler
import com.example.akenasia.R
import com.example.akenasia.achievement.Achievement
import com.example.akenasia.database.PersonnageTable
import com.example.akenasia.database.Position
import com.google.android.gms.maps.model.LatLng
import java.util.concurrent.ThreadLocalRandom


class MainActivity : AppCompatActivity() {


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var appSettingPrefs: SharedPreferences
    lateinit var locationRequest: LocationRequest
    lateinit var marqueurHandler: MarqueurHandler
    lateinit var achievementHandler: AchievementHandler
    lateinit var personnageHandler: PersonnageHandler
    lateinit var statsHandler: StatsHandler
    private lateinit var pos: Position
    private lateinit var markers: HashMap<Int, LatLng>
    val PERMISSION_ID = 1

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
        //Initialisation des données du jeu
        initialisation()
    }

    //function qui ajoute des lieux près du joueur
    private fun FillMap(): HashMap<Int,LatLng>{
        val nb=150
        var randomradius : Int
        var randomLat: Double
        var randomLong: Double
        val markers: HashMap<Int,LatLng> = HashMap()
        pos.refreshLocation()
        for(i in 1..nb){
            randomLat = ThreadLocalRandom.current().nextDouble(0.000,0.10)
            randomLong = ThreadLocalRandom.current().nextDouble(0.000,0.10)
            randomradius = ThreadLocalRandom.current().nextInt(40)
            when (randomradius%4){
                0->markers.put(i,LatLng(pos.getLatitude() + randomLat, pos.getLongitude() +randomLong))
                1->markers.put(i,LatLng(pos.getLatitude() + randomLat, pos.getLongitude() -randomLong))
                2->markers.put(i,LatLng(pos.getLatitude() - randomLat, pos.getLongitude() +randomLong))
                3->markers.put(i,LatLng(pos.getLatitude() - randomLat, pos.getLongitude() -randomLong))
            }
        }
        return markers
    }

    private fun initialisation(){
        //Peuplement de latlng dans la bdd pour le mode OpenWorld
        if(marqueurHandler.view().isEmpty()){
            markers=FillMap()
            for(x in markers){
                marqueurHandler.add(x.key,x.value)
            }
        }
        if(achievementHandler.view().isEmpty()){
            val achievements = Achievement(this)
            achievements.init()
        }
        if (personnageHandler.view().isEmpty()) {
            personnageHandler.create()
        }
        if (statsHandler.getStatsCount().toInt() == 0) {
            statsHandler.create(1)
        }
    }

}



