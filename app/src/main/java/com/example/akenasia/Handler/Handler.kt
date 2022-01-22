package com.example.akenasia.Handler

interface Handler {


    val KEY_ID: String
        get() = "id"
    val KEY_NAME: String
        get() = "name"
    val KEY_LATITUDE: String
        get() = "latitude"
    val KEY_LONGITUDE: String
        get() = "longitude"
    val KEY_PARTIE: String
        get() = "partie"
    val KEY_DESC: String
        get() = "description"
    val KEY_HP: String
        get() = "HP"
    val KEY_ATT: String
        get() = "ATT"
    val KEY_DEF: String
        get() = "DEF"
    val KEY_ARMURE: String
        get() = "armure"
    val KEY_BOUCLIER: String
        get() = "bouclier"
    val KEY_EPEE: String
        get() = "epee"
    val KEY_CHAUSSURES: String
        get() = "chaussures"
    val KEY_TYPE: String
        get() = "type"
    val KEY_ARGENT: String
        get() = "argent"
    val KEY_VISIBLE: String
        get() = "visible"
    val KEY_LASTUPDATED: String
        get() = "last_updated"

}