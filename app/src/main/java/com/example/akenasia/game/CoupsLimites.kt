package com.example.akenasia.game


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.akenasia.home.MainActivity
import com.example.akenasia.R
import com.example.akenasia.handler.PlaceHandler
import com.example.akenasia.handler.PositionHandler
import com.example.akenasia.database.*
import com.example.akenasia.databinding.CoupsLimitesBinding
import kotlinx.android.synthetic.main.coups_limites.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class CoupsLimites() : Fragment(), GameFactory, OnMapReadyCallback {

    override lateinit var pos: Position
    lateinit var placeHandler: PlaceHandler
    lateinit var positionHandler: PositionHandler
    override lateinit var place: Place
    override var isPlay: Boolean = false
    override var i: Int = 0
    override lateinit var googleMap: GoogleMap
    private var _binding: CoupsLimitesBinding? = null
    private val binding get() = _binding!!
    private var thiscontext: Context? = null
    private var lastDistance=0.0
    private var essais=10

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pos = Position(this.requireActivity())
        if (container != null) {
            val id= this.arguments?.getInt("id")
            thiscontext = container.getContext()
            placeHandler = PlaceHandler(thiscontext!!)
            positionHandler = PositionHandler(thiscontext!!)
            place= placeHandler.get(id!!)
        }
        _binding = CoupsLimitesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        cfmap_view.onCreate(savedInstanceState)
        cfmap_view.onResume()
        cfmap_view.getMapAsync(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Cfgoal_X.text=place.getPlaceLat().toString()
        Cfgoal_Y.text=place.getPlaceLong().toString()

        //Rafra??chit la position de l'utilisateur
        CfRefreshBT.setOnClickListener {
            nouvelEssai()
        }
        //Quitte la partie
        CfQuitGameBT.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            this.startActivity(intent)
        }
        //Envoie vers le fragment d'affichage des positions raffra??chies
        CfPositionBT.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id",1)
            bundle.putString("mode","CL")
            findNavController().navigate(R.id.action_Coups_limites_to_Historique,bundle)
        }
    }

    fun getEssais(): Int {
        return essais
    }

    fun nouvelEssai() {
        pos.refreshLocation()
        //tests on simule des positions voir si on obtient les r??sulats attendus
       /*if (essais == 9) {
            pos.setLatitude(10.0)
            pos.setLongitude(10.0)
        }
        if (essais == 8) {
            pos.setLatitude(-10.5)
            pos.setLongitude(-2.0)
        }
        if (essais == 7) {
            pos.setLatitude(0.0)
            pos.setLongitude(1.0)
        }
        if (essais == 6) {
            pos.setLatitude(30.0)
            pos.setLongitude(2.0)
        }
        if (essais == 5) {
            pos.setLatitude(50.0)
            pos.setLongitude(2.0)
        }
        if (essais == 4) {
            pos.setLatitude(32.0)
            pos.setLongitude(-2.0)
        }
        if (essais == 3) {
            pos.setLatitude(-31.0)
            pos.setLongitude(2.0)
        }
        if (essais == 2) {
            pos.setLatitude(31.0)
            pos.setLongitude(2.0)
        }
        if (essais == 1) {
            pos.setLatitude(48.90527388944)
            pos.setLongitude(2.21568703652)
        }*/

        //Ajoute la position r??cup??r??e dans la base de donn??es
        positionHandler.add(
            PositionTable(
                10 - essais,
                pos.getLatitude(),
                pos.getLongitude(),
                1
            )
        )
        pos.getLatitude().toString()
        pos.getLongitude().toString()

        //Calcule de la distance entre la position actuelle et la position objectif
        val distance: Double = pos.calcul_distance(
            pos.getLatitude(),
            pos.getLongitude(),
            Cfgoal_X.text.toString().toDouble(),
            Cfgoal_Y.text.toString().toDouble()
        )

        //Distinction des cas de victoire/d??faite
        if (essais == 1 && distance >= 150) {
            CfresultTV.text = "Dommage ! vous avez perdu ;_;"
            CfRefreshBT.setVisibility(View.GONE);
            CfQuitGameBT.setVisibility(View.VISIBLE)
            CfPositionBT.setVisibility(View.VISIBLE)
        }
        if (distance < 150) {
            //Toast.makeText(this, "Vous avez gagn??!",Toast.LENGTH_SHORT).show()
            CfresultTV.text = "Bravo ! Vous avez gagn??"
            CfRefreshBT.setVisibility(View.GONE);
            CfQuitGameBT.setVisibility(View.VISIBLE)
            CfPositionBT.setVisibility(View.VISIBLE)
        } else {
            if (essais in 2..9) {
                if (distance < lastDistance) {
                    CfresultTV.text = "Vous chauffez !"
                }
                if (distance == lastDistance) {
                    CfresultTV.text = "AFK ?"
                }
                if (distance > lastDistance) {
                    CfresultTV.text = "Vous refroidissez"
                }
            }
        }
        if(essais==0){
            CfRefreshBT.setVisibility(View.GONE);
            CfQuitGameBT.setVisibility(View.VISIBLE)
            CfPositionBT.setVisibility(View.VISIBLE)
            CfessaisTV.text = "Il vous reste " + essais + " essais"
        }
        else{
            lastDistance = distance
            essais--
            CfessaisTV.text = "Il vous reste " + essais + " essais"
            //Toast.makeText(context,distance.toString(),Toast.LENGTH_SHORT).show()
        }
        //Affichage de la position actuelle sur la map avec un marqueur
        val location= LatLng(pos.getLatitude(), pos.getLongitude(),)
        googleMap.clear()
        googleMap.addMarker(MarkerOptions().position(location).title("Position"+(10-essais).toString()))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,15f))
    }

    override fun onMapReady(map: GoogleMap) {
        map.let {
            googleMap = it
        }
    }
}