package com.example.akenasia.openworld.mobs

import android.widget.Toast

class Slime : Monstre() {
    override val name = "Slime"
    override var atk = 1.0
    override val hp = 8.0
    override val texte_spe ="Le slime est en colère, il va vous manger !"


    override fun attaque_spe(): Boolean{
        var nb = (0..100).random()
        if(nb == 100){
            atk = 1000000.0 //attaque où le slime nous one shot (il nous mange)
            return true
        }
        return false
    }
}