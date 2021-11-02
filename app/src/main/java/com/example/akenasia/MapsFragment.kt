package com.example.akenasia

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_maps.*

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        map_view.onCreate(savedInstanceState)
        map_view.onResume()
        map_view.getMapAsync(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

    }

    override fun onMapReady(map: GoogleMap) {
        map?.let {
            googleMap = it

            val location1= LatLng(48.905273887110944, 2.2156870365142827)
            val location2 = LatLng(48.904096168019976, 2.216480970382691)
            val location3 = LatLng(48.903158204219174, 2.2155475616455083)
            googleMap.addMarker(MarkerOptions().position(location1).title("BU"))
            googleMap.addMarker(MarkerOptions().position(location2).title("Crous"))
            googleMap.addMarker(MarkerOptions().position(location3).title("Bat G"))

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location1,17f))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location2,17f))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location3,17f))
        }

    }
}