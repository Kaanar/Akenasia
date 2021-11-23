package com.example.akenasia

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.historique.*
import com.google.android.gms.maps.model.LatLng

class Historique : Fragment(R.layout.fragment_maps), GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var pos: Position
    private lateinit var dbHandler: DatabaseHandler
    private var PartieId=0
    private var thiscontext: Context? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        map_h.onCreate(savedInstanceState)
        map_h.onResume()
        map_h.getMapAsync(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        pos = Position(this.requireActivity())
        if (container != null) {
            thiscontext = container.getContext()
            dbHandler = DatabaseHandler(thiscontext!!)
            PartieId= requireArguments().getInt("id")
        }
        return inflater.inflate(R.layout.historique, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RetourBT.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            this.startActivity(intent)
        }
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
    }

    override fun onMapReady(map: GoogleMap) {
        map?.let {
            googleMap = it

            val databaseHandler: DatabaseHandler = DatabaseHandler(thiscontext!!)
            //calling the viewPlace method of DatabaseHandler class to read the records
            val emp: List<PositionTable> = databaseHandler.viewPosition(PartieId)
            val empArrayId = Array<String>(emp.size) { "0" }
            val empArrayLat = Array<String>(emp.size) { "null" }
            val empArrayLong = Array<String>(emp.size) { "null" }
            val empArrayPartie = Array<String>(emp.size) { "null" }
            var index = 0

            for (e in emp) {

                empArrayId[index] = e.getposId().toString()
                empArrayLat[index] = e.getposLat().toString()
                empArrayLong[index] = e.getposLong().toString()
                empArrayPartie[index] = e.getpartie().toString()

                val marker = LatLng(empArrayLat[index].toDouble(), empArrayLong[index].toDouble())


                googleMap.addMarker(MarkerOptions()
                    .position(marker)
                    .title("Voir info")
                    .snippet("Position : "+(1+e.getposId()).toString())
                )
                index++
            }
            googleMap.setOnInfoWindowClickListener(this)
        }


    }
    override fun onInfoWindowClick(marker: Marker) {
        var dialog = MarkerDialog()
        dialog.setTitle(updateTitle(marker))
        dialog.setInfo(updateInfo(marker))
        dialog.show(parentFragmentManager, "MarkerDialog")
    }
    fun updateTitle(marker : Marker) : String {
        return marker.snippet.toString()
    }

    fun updateInfo(marker: Marker) : String {
        return marker.position.toString()
    }
}

