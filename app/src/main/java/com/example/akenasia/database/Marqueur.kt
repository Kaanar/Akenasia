package com.example.akenasia.database

import com.google.android.gms.maps.model.LatLng

data class Marqueur(var Marqueurid: Int, var MarqueurLocation: LatLng, var MarqueurVisible: Int, var MarqueurLastUpdated: Long){

    @JvmName("getmarqueurid")
    fun getMarqueurId(): Int {
        return Marqueurid
    }

    @JvmName("getmarqueurlocation")
    fun getMarqueurLocation(): LatLng {
        return MarqueurLocation
    }

    @JvmName("getmarqueurvisible")
    fun getMarqueurVisible(): Int {
        return MarqueurVisible
    }

    @JvmName("getmarqueurlastupdated")
    fun getMarqueurLastUpdated(): Long {
        return MarqueurLastUpdated
    }
}

