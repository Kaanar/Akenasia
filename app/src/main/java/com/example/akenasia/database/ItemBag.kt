package com.example.akenasia.database

data class ItemBag(var Itemid: Int, var ItemName: String, var ItemDesc: String) {


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

}