package com.example.akenasia


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import java.lang.Math.*
import kotlin.math.atan2
import kotlin.math.sqrt
import com.example.akenasia.MainActivity.*
import com.google.android.gms.location.*
import kotlin.math.pow


class Position(context: Context) {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var latitude = 0.0
    private var longitude = 0.0
    val context: Context = context
    val PERMISSION_ID = 1010
    private lateinit var activity: MainActivity

    init {

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(
                MainActivity().applicationContext,
                "Vous avez autorisé l'application à récupérer vos coordonnées GPS",
                Toast.LENGTH_SHORT
            ).show()
        }
        fusedLocationProviderClient= FusedLocationProviderClient(context)
        val task =fusedLocationProviderClient.lastLocation.addOnSuccessListener { position: Location? ->
            if (position != null) {
                this.latitude = position.latitude
                this.longitude = position.longitude
            } else {
                this.latitude = 0.0
                this.longitude = 0.0
            }
        }
    }

    //classe pour tester la fonction de calcul de distance
    class PositionTest () {
        fun calcul_distance(lat1 : Double, long1 : Double, lat2 : Double, long2 : Double): Double {
            val d2r = 0.0174532925199433
            val dlong: Double = (long2 - long1) * d2r
            val dlat: Double = (lat2 - lat1) * d2r
            val a: Double = pow(sin(dlat / 2.0), 2.0) + cos(lat1 * d2r) * cos(lat2 * d2r) * pow(sin(dlong / 2.0), 2.0)
            val c: Double = 2 * atan2(sqrt(a), sqrt(1 - a))
            return 6367 * c * 1000 //pour tester avec les mettre, faudra enlever tout ce qu'il y a après la virgule
        }
    }

    fun setLatitude(latitude : Double) {
        this.latitude = latitude
    }

    fun setLongitude(longitude : Double) {
        this.longitude = longitude
    }

    fun getLatitude(): Double {
        return latitude
    }

    fun getLongitude(): Double {
        return longitude
    }


    fun refreshLocation() { //demande la permission de récupérer les coordonnées GPS
        //Si c'est accepté, récupère les coordonnées GPS de l'appareil et les stocke dans l'instance
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(
                MainActivity().applicationContext,
                "Vous avez autorisé l'application à récupérer vos coordonnées GPS",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        val task =
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { position: Location? ->
                if (position != null) {
                    this.latitude = position.latitude
                    this.longitude = position.longitude
                } else {
                    this.latitude = 0.0
                    this.longitude = 0.0
                }
            }


    }
    fun calcul_distance(lat1 : Double, long1 : Double, lat2 : Double, long2 : Double): Double {
        val d2r = 0.0174532925199433
        val dlong: Double = (long2 - long1) * d2r
        val dlat: Double = (lat2 - lat1) * d2r
        val a: Double = pow(sin(dlat / 2.0), 2.0) + cos(lat1 * d2r) * cos(lat2 * d2r) * pow(sin(dlong / 2.0), 2.0)
        val c: Double = 2 * atan2(sqrt(a), sqrt(1 - a))
        return 6367 * c * 1000 //pour tester avec les mettre, faudra enlever tout ce qu'il y a après la virgule
    }
}
