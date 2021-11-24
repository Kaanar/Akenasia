package com.example.akenasia.game

import com.example.akenasia.database.DatabaseHandler
import com.example.akenasia.database.Place
import com.example.akenasia.database.Position
import com.google.android.gms.maps.GoogleMap

interface GameFactory {

    var googleMap: GoogleMap
    var pos: Position
    var dbHandler : DatabaseHandler
    var place: Place
    var isPlay: Boolean
    var i: Int
}