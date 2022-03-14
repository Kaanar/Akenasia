package com.example.akenasia.openworld

import com.example.akenasia.openworld.mobs.*

class CreateMob {
    fun create_mob(): Monstre {
        val nb = (0..10).random()
        val mob: Monstre
        if (nb == 0 || nb == 1 || nb == 2 || nb == 3) {
            mob = Slime()
            return mob
        }
        if (nb == 4 || nb == 5 || nb == 6){
            mob = Serpent()
        return mob
        }

        if(nb == 7 || nb == 8 || nb == 9){
            mob = Orc()
            return mob
        }
        mob = Dragon()
        return mob
    }
}