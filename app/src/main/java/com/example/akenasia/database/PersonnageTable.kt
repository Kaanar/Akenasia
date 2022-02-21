package com.example.akenasia.database

class PersonnageTable (var persoId: String, var persoHp: Double, var persoAtt: Double, var persoDef: Double,var armure: Int,
                            var bouclier: Int, var epee: Int, var chaussures: Int, var argent: Int) {

    @JvmName("setArgent1")
    fun setArgent(argent: Int){
        this.argent= argent
    }
}
