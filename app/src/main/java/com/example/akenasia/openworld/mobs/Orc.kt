package com.example.akenasia.openworld.mobs

class Orc : Monstre() {
    override val name = "Orc"
    override var atk = 10.0
    override var hp = 40.0 //augmentation car augmentation des pv du slime
    override val texte_spe ="L'orc pète un câble et son attaque triple !"


    override fun attaque_spe(): Boolean{
        var nb = (1..5).random()
        if(nb == 1){
            atk *= 3.0
            return true
        }
        return false
    }
}