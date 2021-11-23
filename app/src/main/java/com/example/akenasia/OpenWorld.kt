package com.example.akenasia

import android.content.Intent
import android.os.Bundle
import android.widget.Chronometer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController

import com.example.akenasia.databinding.ActivityOpenworldBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest
import kotlinx.android.synthetic.main.chronometre.*
import kotlinx.android.synthetic.main.content_map.*


class OpenWorld : AppCompatActivity(),OnMapReadyCallback, GoogleMap.OnPoiClickListener {


    private lateinit var pos: Position
    private var isPlay: Boolean = false
    private lateinit var binding: ActivityOpenworldBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var googleMap: GoogleMap
    private lateinit var chronometre: Chronometer
    private var cameraFocus: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenworldBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Mise en place d'un navcontroller pour d'eventuels fragments
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.include3) as NavHostFragment?
        val navController = navHostFragment?.navController
        if (navController != null) {
            appBarConfiguration = AppBarConfiguration(navController.graph)
        }
        if (navController != null) {
            setupActionBarWithNavController(navController, appBarConfiguration)
        }

        //Initialisation de la position
        pos = Position(this)

        //démarrage du chrono
        chronometre = binding.OWChrono
        chronometre.start()

        //Initialisation de la map
        OWmap_view.onCreate(savedInstanceState)
        OWmap_view.onResume()
        OWmap_view.getMapAsync(this)

        //Implémentation des différents choix du menu
        binding.NavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                    R.id.QuitClick -> {
                        val intent = Intent(this, MainActivity::class.java)
                        this.startActivity(intent)
                        true
                    }
                    R.id.BagClick -> {
                        val intent = Intent(this, Bag::class.java)
                        this.startActivity(intent)
                        true
                    }
                    else -> {
                        Toast.makeText(this, "Available soon",Toast.LENGTH_LONG).show()
                       true
                    }
                }
            true
        }

        CameraSwitch.setOnClickListener(){
            cameraFocus = cameraFocus != true
        }
    }


    override fun onMapReady(map: GoogleMap) {
        map?.let {
            googleMap = it
            googleMap.setOnPoiClickListener(this)

            //rafraîchit la position du joueur à chaque tik
            chronometre.onChronometerTickListener = Chronometer.OnChronometerTickListener {
                pos.refreshLocation()
                val currentTime: String = chronometre.getText().toString()
                val location= LatLng(pos.getLatitude(), pos.getLongitude(),)
                googleMap.clear()
                googleMap.addMarker(MarkerOptions().position(location).title("Position"))
                if (cameraFocus) {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,15f))
                }
            }
        }

    }

    //Implémentation de la méthode lorsqu'on click sur un POI
    override fun onPoiClick(poi: PointOfInterest) {
        pos.refreshLocation()
        //calcul de la distance
        val distance: Double = pos.calcul_distance(
            pos.getLatitude(),
            pos.getLongitude(),
            poi.latLng.latitude,
            poi.latLng.longitude,
        )
        if (distance < 500) {
            var dialog = PoiDialog()
            dialog.setName(updateTitle(poi))
            dialog.setLatLong(updateInfo(poi))
            //dialog.show(parentFragmentManager, "PoiDialog") //ça pareil ça compile pas
        }
        else {
            Toast.makeText(this, "Trop loin", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateTitle(poi: PointOfInterest) : String {
        return poi.name
    }

    fun updateInfo(poi: PointOfInterest) : String {
        return poi.latLng.latitude.toString() +"\n" + poi.latLng.longitude.toString()
    }
}