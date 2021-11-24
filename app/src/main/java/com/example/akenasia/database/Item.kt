package com.example.akenasia.database

import android.graphics.Bitmap

data class Item(var Itemid: Int, var ItemName: String, var ItemDesc: String, var ItemAttack : Int,var ItemDefense: Int) {


    @JvmName("Itemid")
    fun getItemid(): Int {
        return Itemid
    }

    @JvmName("getPlaceLat1")
    fun getItemName(): String {
        return ItemName
    }

    @JvmName("getitemDesc")
    fun getitemDesc(): String {
        return ItemDesc
    }

    @JvmName("getItemPointAttack")
    fun getItemPointAttack(): Int {
        return ItemAttack
    }

    @JvmName("getItemPointDefense")
    fun getItemPointDefense(): Int{
        return ItemDefense
    }

}