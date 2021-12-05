package com.example.akenasia.database

data class Item(var Itemid: Int, var ItemName: String, var ItemDesc: String) {

    @JvmName("getItemId")
    fun getItemid(): Int {
        return Itemid
    }

    @JvmName("getItemName1")
    fun getItemName(): String {
        return ItemName
    }

    @JvmName("getitemDesc")
    fun getitemDesc(): String {
        return ItemDesc
    }



}