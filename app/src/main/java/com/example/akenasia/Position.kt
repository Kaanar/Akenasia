package com.example.akenasia


import android.Manifest
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

    //private var fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity())
    private var latitude=0.0
    private var longitude=0.0

    init {
        /*val client=fusedLocationProviderClient
        if (ActivityCompat.checkSelfPermission(
                MainActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                MainActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details
        }
        client.lastLocation.addOnSuccessListener { position: Location? ->
            if (position != null) {
                this.latitude = position.latitude
                this.longitude = position.longitude
            } else {
                this.latitude = 0.0
                this.longitude = 0.0
            }
        }*/
    }

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
    //}
    /*fun getFusedLocationProviderClient(): FusedLocationProviderClient {
        return fusedLocationProviderClient*/
    }

    fun getCoordonnees() { //celle là doit demander la permission et si elle est accepté retourner les coordonnées dans un toast
        //dans un toast pour voir si ça marche, après on pourra les stocker ou les mettre ailleurs
        LocationServices.getFusedLocationProviderClient(MainActivity())
        /*val task = fusedLocationProviderClient.lastLocation

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
            else{
                Toast.makeText(MainActivity().applicationContext,"erreur", Toast.LENGTH_SHORT).show()*/

           // }
       // }
    }
}
