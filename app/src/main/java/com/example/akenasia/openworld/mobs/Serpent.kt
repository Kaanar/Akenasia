package com.example.akenasia.openworld.mobs

class Serpent : Monstre() {
    override val name = "Serpent"
    override var atk = 8.0
    override var hp = 30.0 //augmentation car augmentation des pv du slime
    override val texte_spe ="Le serpent de l'attaque !"


    override fun attaque_spe(): Boolean{
        var nb = (1..5).random()
        if(nb == 1){
            atk += 5
            return true
        }
        return false
    }
}