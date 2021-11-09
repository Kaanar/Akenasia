package com.example.akenasia

import android.content.Context
import com.example.akenasia.databinding.ChronometreBinding
import com.google.android.gms.maps.GoogleMap

interface GameFactory {

    var googleMap: GoogleMap
    var pos: Position
    var dbHandler : DatabaseHandler
    var place: Place
    var isPlay: Boolean
    var i: Int
}