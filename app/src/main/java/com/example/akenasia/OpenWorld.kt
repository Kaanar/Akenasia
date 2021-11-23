package com.example.akenasia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.PointOfInterest
import kotlinx.android.synthetic.main.chronometre.*

class OpenWorld : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnPoiClickListener {

    lateinit var googleMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_world)
    }

    override fun onMapReady(p0: GoogleMap) {
        p0.let {
            googleMap.setOnPoiClickListener(this)
        }
    }

    override fun onPoiClick(poi: PointOfInterest) {
        var dialog = PoiDialog()
        dialog.setName(updateTitle(poi))
        dialog.setLatLong(updateInfo(poi))
        //dialog.show(parentFragmentManager, "PoiDialog")
    }

    fun updateTitle(poi: PointOfInterest) : String {
        return poi.name
    }

    fun updateInfo(poi: PointOfInterest) : String {
        return poi.latLng.latitude.toString() +"\n" + poi.latLng.longitude.toString()
    }
}