package com.example.akenasia.openworld.mobs

class Dragon : Monstre() {
    override val name = "Dragon"
    override var atk = 100.0
    override val hp = 1000.0
    override val texte_spe ="Le dragon augmente sa puissance !"


    override fun attaque_spe(): Boolean{
        var nb = (0..5).random()
        if(nb == 5){
            atk += 15.0 //le dragon se boost
            return true
        }
        return false
    }
}