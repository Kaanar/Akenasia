package com.example.akenasia


import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class Position(context: Context) {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var latitude=0.0
    private var longitude=0.0
    val context:Context = context


    init {}

    fun getLatitude(): Double {
        return latitude
    }

    fun getLongitude(): Double {
        return longitude
    }


    fun refreshLocation() { //demande la permission de récupérer les coordonnées GPS
        //Si c'est accepté,récupère les coordonnées GPS de l'appareil et les stocke dans l'instance
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(context)

        if(ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        )
        {
            Toast.makeText(MainActivity().applicationContext,"Vous avez autorisé l'application à récupérer vos coordonnées GPS", Toast.LENGTH_SHORT).show()
            return
        }
        val task = fusedLocationProviderClient.lastLocation.addOnSuccessListener { position: Location? ->
            if (position != null) {
                this.latitude = position.latitude
                this.longitude = position.longitude
            } else {
                this.latitude = 0.0
                this.longitude = 0.0
            }
        }


        /*task.addOnSuccessListener {
            if(it != null){
                Toast.makeText(MainActivity().applicationContext,"${it.latitude} ${it.longitude}", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(MainActivity().applicationContext,"erreur", Toast.LENGTH_SHORT).show()

           }
        }*/
    }
}
