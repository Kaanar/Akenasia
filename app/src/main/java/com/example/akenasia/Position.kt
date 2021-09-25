package com.example.akenasia


import android.app.Application
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient


class Position {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    fun checkLocationPermission() { //passé en publique pour fonctionner dans MainActivity
    //fonction pour test l'appel de fonction d'une classe non activity sur la mainActivity
        /*if(ActivityCompat.checkSelfPermission(AppCompatActivity() , android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                AppCompatActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        )
        {
            ActivityCompat.requestPermissions(, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }*/
        Toast.makeText(AppCompatActivity().applicationContext,"test", Toast.LENGTH_SHORT).show()
    }

    fun getCoordonnees() { //celle là doit demander la permission et si elle est accepté retourner les coordonnées dans un toast
        //dans un toast pour voir si ça marche, après on pourra les stocker ou les mettre ailleurs
        val task = fusedLocationProviderClient.lastLocation

        if(ActivityCompat.checkSelfPermission(MainActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        )
        {
            ActivityCompat.requestPermissions(MainActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }
        task.addOnSuccessListener {
            if(it != null){
                Toast.makeText(MainActivity().applicationContext,"${it.latitude} ${it.longitude}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
