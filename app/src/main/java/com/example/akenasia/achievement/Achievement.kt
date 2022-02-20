package com.example.akenasia.achievement

import android.content.Context
import com.example.akenasia.Handler.AchievementHandler

class Achievement() {
    //var achievementHandler= AchievementHandler(context)

    fun data_init(): ArrayList<String>{
        //Création des succès des marqueurs
        val achievements: ArrayList<String> = ArrayList()
        val marqueurs_treshold =intArrayOf(10,20,30,40,50,75,100,150,200)
        for(x in marqueurs_treshold){
            achievements.add("Fouiller $x lieux")
        }

        //Création des succès des monstres vaincus
        val monstres_treshold =intArrayOf(1,5,10,15,20,35,50,70,100)
        for(x in monstres_treshold){
            achievements.add("Vaincre $x ennemis")
        }


        //Création des succès des items récupérés
        val items_treshold =intArrayOf(1,5,10,15,20,35,50,70,100)
        for(x in items_treshold){
            achievements.add("Récupérer $x items")
        }

        return achievements
    }

    /*fun init(){
        //Création des succès des marqueurs
        val marqueurs_treshold =intArrayOf(10,20,30,40,50,75,100,150,200)
        var id=1
        for(x in marqueurs_treshold){
            achievementHandler.add(id, "Fouiller $x lieux")
            id++
        }

        //Création des succès des monstres vaincus
        val monstres_treshold =intArrayOf(1,5,10,15,20,35,50,70,100)
        for(x in monstres_treshold){
            achievementHandler.add(id, "Vaincre $x ennemis")
            id++
        }


        //Création des succès des items récupérés
        val items_treshold =intArrayOf(1,5,10,15,20,35,50,70,100)
        for(x in items_treshold){
            achievementHandler.add(id,"Récupérer $x items")
            id++
        }

    }*/
}