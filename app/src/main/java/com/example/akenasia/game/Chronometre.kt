package com.example.akenasia.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.akenasia.home.MainActivity
import com.example.akenasia.openworld.PoiDialog
import com.example.akenasia.R
import com.example.akenasia.handler.PlaceHandler
import com.example.akenasia.handler.PositionHandler
import com.example.akenasia.database.*
import com.example.akenasia.databinding.ChronometreBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest
import kotlinx.android.synthetic.main.chronometre.*
import kotlinx.android.synthetic.main.chronometre.Chmap_view


class Chronometre() : Fragment(), GameFactory, OnMapReadyCallback, GoogleMap.OnPoiClickListener {


    override lateinit var pos: Position
    lateinit var placeHandler: PlaceHandler
    lateinit var positionHandler: PositionHandler
    override lateinit var place: Place
    override var isPlay: Boolean = false
    override var i: Int = 0
    override lateinit var googleMap: GoogleMap
    private var _binding: ChronometreBinding? = null
    private val binding get() = _binding!!
    private var thiscontext: Context? = null
    private var lastDistance=0.0
    private lateinit var chronometre: Chronometer


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pos = Position(this.requireActivity())
        if (container != null) {
            val id= this.arguments?.getInt("id")
            thiscontext = container.context
            placeHandler = PlaceHandler(thiscontext!!)
            positionHandler = PositionHandler(thiscontext!!)
            place= placeHandler.get(id!!)
        }

        _binding = ChronometreBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Chmap_view.onCreate(savedInstanceState)
        Chmap_view.onResume()
        Chmap_view.getMapAsync(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chronometre = chronoMterPlay
        Chgoal_X.text=place.getPlaceLat().toString()
        Chgoal_Y.text=place.getPlaceLong().toString()

        //Actualisation de la position du joueur
        pos.refreshLocation()
        if (!isPlay) {
            chronometre.base = SystemClock.elapsedRealtime() + 300000
            chronometre.start()
            isPlay = true
        }

        //Quitte la partie
        ChQuitGameBT.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            this.startActivity(intent)
        }
        //Envoie vers le fragment d'affichage des positions raffra??chies
        ChPositionBT.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id",1)
            bundle.putString("mode","Chronometre")
            findNavController().navigate(R.id.Histo,bundle)
        }
    }

    fun nouvelEssai() {
        pos.refreshLocation()

        positionHandler.add(
            PositionTable(
                i,
                pos.getLatitude(),
                pos.getLongitude(),
                1
            )
        )

        //calcul de la distance
        val distance: Double = pos.calcul_distance(
            pos.getLatitude(),
            pos.getLongitude(),
            Chgoal_X.text.toString().toDouble(),
            Chgoal_Y.text.toString().toDouble()
        )

        //Condition d'arr??t -> Victoire
        if (distance < 150) {
            //Toast.makeText(this, "Vous avez gagn??!",Toast.LENGTH_SHORT).show()
            isPlay = false
            chronometre.stop()
            ChresultTV.text = "Bravo ! Vous avez gagn??"
            ChQuitGameBT.setVisibility(View.VISIBLE)
            ChPositionBT.setVisibility(View.VISIBLE)
        }
        else {
                if (distance < lastDistance) {
                    ChresultTV.text = "Vous chauffez !"
                }
                if (distance == lastDistance) {
                    ChresultTV.text = "AFK ?"
                }
                if (distance > lastDistance) {
                    ChresultTV.text = "Vous refroidissez"
                }
            }
        i++
        lastDistance = distance
        //Affichage de la position actuelle sur la map avec un marqueur
        val location= LatLng(pos.getLatitude(), pos.getLongitude(),)
        googleMap.clear()
        googleMap.addMarker(MarkerOptions().position(location).title("Position"))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,15f))

    }

    override fun onMapReady(p0: GoogleMap) {
        p0.let {
            googleMap = it
            googleMap.setOnPoiClickListener(this)
        }
        //V??rifie chaque tick du chrono et rafra??chit la position de l'utilisateur
        chronometre.onChronometerTickListener = Chronometer.OnChronometerTickListener {
            nouvelEssai()
            val currentTime: String = chronometre.getText().toString()
            //On arr??te la partie lorsque le chrono arrive ?? 0
            if (currentTime == "00:00") {
                chronometre.stop()
                isPlay = false
                ChresultTV.text = "Temps ??coul??, c'est perdu ;_;"
                ChQuitGameBT.setVisibility(View.VISIBLE)
                ChPositionBT.setVisibility(View.VISIBLE)
            }
        }
    }

    override fun onPoiClick(poi: PointOfInterest) {
        var dialog = PoiDialog()
        dialog.setName(updateTitle(poi))
        dialog.setLatLong(updateInfo(poi))
        dialog.show(parentFragmentManager, "PoiDialog")
    }

    fun updateTitle(poi: PointOfInterest) : String {
        return poi.name
    }

    fun updateInfo(poi: PointOfInterest) : String {
        return poi.latLng.latitude.toString() +"\n" + poi.latLng.longitude.toString()
    }

}