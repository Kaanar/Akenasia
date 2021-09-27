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
import java.lang.Math.pow
import java.lang.Math.sqrt
import kotlin.math.pow


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
    }

    //calcule la distance en km, x1000 pour l'avoir en mètres, à tester en extérieur pour voir ce que ça donne quand on se déplace
    fun calcul_distance(lat1 : Double, long1 : Double, lat2 : Double, long2 : Double, d2r : Double = 0.0174532925199433): Double {
        val dlong: Double = ((long2 - long1) * d2r).pow(2.0)
        val dlat: Double = (lat2 - lat1) * d2r.pow(2.0)
        val a: Double = kotlin.math.sin(dlat / 2.0).pow(2.0) + kotlin.math.cos(lat1 * d2r) * kotlin.math.cos(
            lat2 * d2r
        ) * kotlin.math.sin(dlong / 2.0).pow(2.0)
        val c: Double = 2 * kotlin.math.atan2(kotlin.math.sqrt(a), kotlin.math.sqrt(1 - a))
        val result: Double = sqrt(dlong+dlat)
        return result* 1000 //pour tester avec les mettre, faudra enlever tout ce qu'il y a après la virgule
    }

}
