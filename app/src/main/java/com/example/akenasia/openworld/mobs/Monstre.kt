package com.example.akenasia.openworld.mobs

open class Monstre {
    open val name = "Monstre"
    open val atk = 0.0
    open val hp = 0.0
    open val texte_spe ="Texte"

    open fun attaque_spe(): Boolean{
        return false
    }
}

