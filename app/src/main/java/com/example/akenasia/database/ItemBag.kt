package com.example.akenasia.database

data class ItemBag(var ItemBagId: Int, var ItemId : Int ) {


    @JvmName("Itemid")
    fun getItemid(): Int {
        return ItemBagId
    }

    @JvmName("getPlaceLat1")
    fun getItemId(): Int {
        return ItemId
    }
}