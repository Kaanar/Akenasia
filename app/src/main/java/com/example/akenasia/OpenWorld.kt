package com.example.akenasia

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.akenasia.databinding.ChronometreBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest
import kotlinx.android.synthetic.main.activity_open_world.*
import kotlinx.android.synthetic.main.chronometre.*
import kotlinx.android.synthetic.main.chronometre.Chmap_view
import kotlinx.android.synthetic.main.chronometre.chronoMterPlay


class OpenWorld() : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnPoiClickListener {


    private lateinit var pos: Position
    private var isPlay: Boolean = false
    private lateinit var googleMap: GoogleMap
    private var _binding: ChronometreBinding? = null
    private val binding get() = _binding!!
    private var cameraFocus: Boolean = true
    private lateinit var chronometre: Chronometer


    fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pos = Position(this)
        if (container != null) {
        }
        _binding = ChronometreBinding.inflate(inflater, container, false)
        return binding.root

    }

    fun onActivityCreated(savedInstanceState: Bundle?) { //j'ai enlevé les override des fonctions sinon ça compile pas
        //super.onActivityCreated(savedInstanceState)

        Chmap_view.onCreate(savedInstanceState)
        Chmap_view.onResume()
        Chmap_view.getMapAsync(this)
    }

    fun onViewCreated(view: View, savedInstanceState: Bundle?) { //toutes les fonctions griséés c'est parce qu'elles ont
        //super.onViewCreated(view, savedInstanceState) //plus d'override mais ça doit les "désactiver" je pense
        chronometre = chronoMterPlay

        //Actualisation de la position du joueur
        if (!isPlay) {
            chronometre.base = SystemClock.elapsedRealtime() + 300000
            chronometre.start()
            isPlay = true
        }
        //Rafraîchit la position de l'utilisateur

        CameraSwitch.setOnClickListener(){
            cameraFocus = cameraFocus != true
        }
    }

    fun click(poi: PointOfInterest) {
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

    override fun onMapReady(p0: GoogleMap) {
        p0.let {
            googleMap = it
            googleMap.setOnPoiClickListener(this)
        }
        //Vérifie chaque tick du chrono
        chronometre.onChronometerTickListener = Chronometer.OnChronometerTickListener {
            val currentTime: String = chronometre.getText().toString()
            val location= LatLng(pos.getLatitude(), pos.getLongitude(),)
            googleMap.clear()
            googleMap.addMarker(MarkerOptions().position(location).title("Position"))
            if (cameraFocus) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,15f))
            }
        }
    }

    override fun onPoiClick(poi: PointOfInterest) {
        click(poi)
    }

    fun updateTitle(poi: PointOfInterest) : String {
        return poi.name
    }

    fun updateInfo(poi: PointOfInterest) : String {
        return poi.latLng.latitude.toString() +"\n" + poi.latLng.longitude.toString()
    }
}