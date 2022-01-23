package com.example.akenasia.database

data class PersonnageTable (var persoId: Int, var persoHp: Double, var persoAtt: Double, var persoDef: Double,var armure: Int,
                            var bouclier: Int, var epee: Int, var chaussures: Int, var argent: Int) {
    @JvmName("getpersoId1")
    fun getpersoId(): Int {
        return persoId
    }

    @JvmName("getpersoHp1")
    fun getpersoHp(): Double {
        return persoHp
    }

    @JvmName("getpersoAtt1")
    fun getpersoAtt(): Double {
        return persoAtt
    }

    @JvmName("getpersoDef1")
    fun getpersoDef(): Double {
        return persoDef
    }

    @JvmName("getArmure1")
    fun getArmure():Int {
        return armure
    }

    @JvmName("getBouclier1")
    fun getBouclier():Int {
        return bouclier
    }

    @JvmName("getEpee1")
    
    fun getEpee():Int {
        return epee
    }

    @JvmName("getChaussures1")
    fun getChaussures():Int {
        return chaussures
    }

    @JvmName("getArgent1")
    fun getArgent():Int {
        return argent
    }

    @JvmName("setArgent1")
    fun setArgent(somme : Int) {
        argent += somme
    }
}
