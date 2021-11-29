package com.example.akenasia.database

data class Item(var Itemid: Int,var ItemType: String, var ItemName: String, var ItemDesc: String) {

    @JvmName("getItemId")
    fun getItemid(): Int {
        return Itemid
    }

    @JvmName("getItemType1")
    fun getItemType(): String {
        return ItemType
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