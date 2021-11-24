package com.example.akenasia.database

data class Place (var placeId: Int, var placeName: String, var placeLat: Double, var placeLong: Double) {
    @JvmName("getPlaceId1")
    fun getPlaceId(): Int {
        return placeId
    }

    @JvmName("getPlaceLat1")
    fun getPlaceLat(): Double {
        return placeLat
    }

    @JvmName("getPlaceName1")
    fun getPlaceName(): String {
        return placeName
    }

    @JvmName("getPlaceLong1")
    fun getPlaceLong(): Double {
        return placeLong
    }
}