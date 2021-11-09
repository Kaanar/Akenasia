package com.example.akenasia


import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.example.akenasia.MainActivity.*
import com.google.android.gms.location.*
import java.lang.Math.*
import kotlin.math.atan2
import kotlin.math.sqrt
import com.google.android.gms.location.LocationSettingsStatusCodes

import android.content.IntentSender
import android.content.IntentSender.SendIntentException

import com.google.android.gms.common.api.ResolvableApiException

import com.google.android.gms.common.api.ApiException

import android.widget.Toast

import com.google.android.gms.location.LocationSettingsResponse

import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.test.core.app.ApplicationProvider

import com.google.android.gms.tasks.OnCompleteListener

import androidx.test.core.app.ApplicationProvider.getApplicationContext

import com.google.android.gms.location.LocationServices

import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.tasks.Task


class Position(context: Context) {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var latitude = 0.0
    private var longitude = 0.0
    val context: Context = context
    val PERMISSION_ID = 1010
    private lateinit var activity: MainActivity
    private lateinit var locationRequest: LocationRequest


    init {

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

        }
        fusedLocationProviderClient = FusedLocationProviderClient(context)
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

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
    }

    //classe pour tester la fonction de calcul de distance
    class PositionTest() {
        fun calcul_distance(lat1: Double, long1: Double, lat2: Double, long2: Double): Double {
            val d2r = 0.0174532925199433
            val dlong: Double = (long2 - long1) * d2r
            val dlat: Double = (lat2 - lat1) * d2r
            val a: Double = pow(
                sin(dlat / 2.0),
                2.0
            ) + cos(lat1 * d2r) * cos(lat2 * d2r) * pow(sin(dlong / 2.0), 2.0)
            val c: Double = 2 * atan2(sqrt(a), sqrt(1 - a))
            return 6367 * c * 1000 //pour tester avec les mettre, faudra enlever tout ce qu'il y a après la virgule
        }
    }

    fun setLatitude(latitude: Double) {
        this.latitude = latitude
    }

    fun setLongitude(longitude: Double) {
        this.longitude = longitude
    }

    fun getLatitude(): Double {
        return latitude
    }

    fun getLongitude(): Double {
        return longitude
    }

    fun refreshLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {

                LocationServices.getFusedLocationProviderClient(context)
                    .requestLocationUpdates(locationRequest, object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult) {
                            super.onLocationResult(locationResult)
                            LocationServices.getFusedLocationProviderClient(context)
                                .removeLocationUpdates(this)
                            if (locationResult != null && locationResult.locations.size > 0) {
                                val index = locationResult.locations.size - 1
                                val latitude= locationResult.locations[index].latitude
                                val longitude= locationResult.locations[index].longitude
                                setLatitude(latitude)
                                setLongitude(longitude)
                            }
                            else{
                                latitude=0.0
                                longitude=0.0
                            }
                        }
                    }, Looper.getMainLooper())
            } else {
                turnOnGPS();
            }

        } else {
            //
        }
    }

    private fun turnOnGPS() {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)
        val result: Task<LocationSettingsResponse> = LocationServices.getSettingsClient(context)
            .checkLocationSettings(builder.build())
        result.addOnCompleteListener(OnCompleteListener<LocationSettingsResponse?> { task ->
            try {
                val response = task.getResult(ApiException::class.java)
                Toast.makeText(context, "GPS is already tured on", Toast.LENGTH_SHORT)
                    .show()
            } catch (e: ApiException) {
                when (e.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                        val resolvableApiException = e as ResolvableApiException
                        resolvableApiException.startResolutionForResult(context as Activity, 2)
                    } catch (ex: SendIntentException) {
                        ex.printStackTrace()
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    }
                }
            }
        })
    }
    //demande la permission de récupérer les coordonnées GPS
    //Si c'est accepté, récupère les coordonnées GPS de l'appareil et les stocke dans l'instance
    /*fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED) {
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
                }*/


    fun calcul_distance(lat1: Double, long1: Double, lat2: Double, long2: Double): Double {
        val d2r = 0.0174532925199433
        val dlong: Double = (long2 - long1) * d2r
        val dlat: Double = (lat2 - lat1) * d2r
        val a: Double = pow(sin(dlat / 2.0), 2.0) + cos(lat1 * d2r) * cos(lat2 * d2r) * pow(
            sin(dlong / 2.0),
            2.0
        )
        val c: Double = 2 * atan2(sqrt(a), sqrt(1 - a))
        return 6367 * c * 1000 //pour tester avec les mettre, faudra enlever tout ce qu'il y a après la virgule
    }
}
