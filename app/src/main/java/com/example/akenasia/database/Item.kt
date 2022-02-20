package com.example.akenasia.database

data class Item(var Itemid: Int,var ItemType: String, var ItemName: String, var ItemDesc: String, var ItemAtt: Double, var ItemDef: Double, var nb_Upgrade : Int, var ItemPrix : Int) {

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

    @JvmName("getItemDesc1")
    fun getItemDesc(): String {
        return ItemDesc
    }
    @JvmName("getItemAtt1")
    fun getItemAtt(): Double {
        return ItemAtt
    }

    @JvmName("getItemDef1")
    fun getItemDef(): Double {
        return ItemDef
    }

    fun getItemNbUpgrade() : Int {
        return nb_Upgrade
    }

    @JvmName("getItemPrix1")
    fun getItemPrix() : Int {
        return ItemPrix
    }

}