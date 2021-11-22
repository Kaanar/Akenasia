package com.example.akenasia

data class Item(var itemId: Int, var itemName: String, var itemDesc: String){
    @JvmName("getItemId1")
    fun getItemId1(): Int {
        return itemId
    }

    @JvmName("getItemName1")
    fun getItemName1(): String {
        return itemName
    }

    @JvmName("getItemDesc1")
    fun getItemDesc1(): String {
        return itemDesc
    }
}
